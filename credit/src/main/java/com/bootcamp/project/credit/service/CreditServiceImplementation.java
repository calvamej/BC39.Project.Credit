package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.CreditDailyReportEntity;
import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.entity.CreditReportEntity;
import com.bootcamp.project.credit.exception.CustomInformationException;
import com.bootcamp.project.credit.exception.CustomNotFoundException;
import com.bootcamp.project.credit.repository.CreditRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class CreditServiceImplementation implements CreditService{
    private static Logger Log = Logger.getLogger(CreditServiceImplementation.class);
    @Autowired
    private CreditRepository creditRepository;

    @Override
    public Flux<CreditEntity> getAll() {
        return creditRepository.findAll().switchIfEmpty(Mono.error(new CustomNotFoundException("Credits not found")));
    }
    @Override
    public Mono<CreditEntity> getOne(String creditNumber) {
        return creditRepository.findAll().filter(x -> x.getCreditNumber() != null && x.getCreditNumber().equals(creditNumber)).next();
    }

    @Override
    public Mono<CreditEntity> save(CreditEntity colEnt) {
        colEnt.setCreateDate(new Date());
        return creditRepository.save(colEnt);
    }

    @Override
    public Mono<CreditEntity> update(String creditNumber, double currentDebt) {
        return getOne(creditNumber).flatMap(c -> {
            c.setCurrentDebt(currentDebt);
            c.setModifyDate(new Date());
            return creditRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }

    @Override
    public Mono<Void> delete(String creditNumber) {
        return getOne(creditNumber)
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")))
                .flatMap(c -> {
                    return creditRepository.delete(c);
                });
    }
    @Override
    public Mono<CreditEntity> registerPersonalCredit(CreditEntity colEnt) {

        //PRODUCT CODE: CC = CREDIT CARD, BC = BUSINESS CREDIT, PC = PERSONAL CREDIT.
        if(colEnt.getProductCode().equals("PC"))
        {
            colEnt.setCreateDate(new Date());
            return creditRepository.findAll().filter(x -> x.getClientDocumentNumber() != null
                            && x.getClientDocumentNumber().equals(colEnt.getClientDocumentNumber())
                            && x.getProductCode() != null && x.getProductCode().equals("PC"))
                    .next()
                    .switchIfEmpty(creditRepository.save(colEnt));
        }
        else if(colEnt.getProductCode().equals("CC"))
        {
            colEnt.setCreateDate(new Date());
            return creditRepository.findAll().filter(x -> x.getCreditNumber() != null && x.getCreditNumber().equals(colEnt.getCreditNumber())).next()
                    .switchIfEmpty(creditRepository.save(colEnt));
        }
        else
        {
            return Mono.error(new CustomInformationException("Personal clients can only register Personal Credits and Credit Cards"));
        }
    }
    @Override
    public Mono<CreditEntity> registerCompanyCredit(CreditEntity colEnt) {

        //PRODUCT CODE: CC = CREDIT CARD, BC = BUSINESS CREDIT, PC = PERSONAL CREDIT.
        if(colEnt.getProductCode().equals("BC") || colEnt.getProductCode().equals("CC"))
        {
            colEnt.setCreateDate(new Date());
            return creditRepository.findAll().filter(x -> x.getCreditNumber() != null && x.getCreditNumber().equals(colEnt.getCreditNumber())).next()
                    .switchIfEmpty(creditRepository.save(colEnt));
        }
        else
        {
            return Mono.error(new CustomInformationException("Business clients can only register Business Credits or Credit Cards"));
        }
    }
    @Override
    public Mono<CreditEntity> payCredit(String creditNumber, double amount)
    {
        return getOne(creditNumber).flatMap(c -> {
            c.setCurrentDebt(c.getCurrentDebt() - amount);
            c.setModifyDate(new Date());
            return creditRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }
    @Override
    public Mono<CreditEntity> addCreditCardConsume(String creditNumber, double amount)
    {
        //PRODUCT CODE: CC = CREDIT CARD, BC = BUSINESS CREDIT, PC = PERSONAL CREDIT.
        return creditRepository.findAll().filter(x -> x.getCreditNumber() != null && x.getCreditNumber().equals(creditNumber)
                && x.getProductCode() != null && x.getProductCode().equals("CC")
                && (x.getCreditLimit() >= amount + x.getCurrentDebt())).next().flatMap(c -> {
                c.setCurrentDebt(c.getCurrentDebt() + amount);
                c.setModifyDate(new Date());
                return creditRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found, not a Credit Card or surpassed credit limit amount.")));
    }
    @Override
    public Flux<CreditEntity> getByClient(String clientDocumentNumber)
    {
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber() != null
                        && x.getClientDocumentNumber().equals(clientDocumentNumber))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
    @Override
    public Mono<Double> getCurrentDebt(String creditNumber) {
        //get current debt
        return getOne(creditNumber)
                .map(x -> x.getCurrentDebt())
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }
    @Override
    public Mono<Double> getCreditCardAvailableBalance(String creditNumber)
    {
        //PRODUCT CODE: CC = CREDIT CARD, BC = BUSINESS CREDIT, PC = PERSONAL CREDIT.
        return creditRepository.findAll().filter(x -> x.getCreditNumber() != null && x.getCreditNumber().equals(creditNumber)
                && x.getProductCode() != null && x.getProductCode().equals("CC")).next()
                .map(x -> (x.getCreditLimit() - x.getCurrentDebt()))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found or not a Credit Card Credit")));
    }
    @Override
    public Mono<Boolean> checkDueDebtByClient(String clientDocumentNumber)
    {
        Date today = new Date();
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber() != null && x.getClientDocumentNumber().equals(clientDocumentNumber)
                        && x.getDebtDueDate() != null && x.getDebtDueDate().before(today)
                        && x.getCurrentDebt() >0)
                .hasElements()
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
    @Override
    public Flux<CreditReportEntity> getCreditsByDates(Date initialDate, Date finalDate)
    {
        return creditRepository.findAll()
                .filter(c -> c.getCreateDate() != null && c.getCreateDate().after(initialDate) && c.getCreateDate().before(finalDate))
                .groupBy(CreditEntity::getProductCode)
                .flatMap(a -> a
                        .collectList().map(list ->
                                new CreditReportEntity(a.key(), list)))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Credits not found between the given dates.")));
    }
    @Override
    public Flux<CreditReportEntity> getCreditsByClient(String clientDocumentNumber)
    {
        return creditRepository.findAll()
                .filter(c -> c.getClientDocumentNumber() != null && c.getClientDocumentNumber().equals(clientDocumentNumber))
                .groupBy(CreditEntity::getProductCode)
                .flatMap(a -> a
                        .collectList().map(list ->
                                new CreditReportEntity(a.key(), list)))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
    @Override
    public Flux<CreditDailyReportEntity> getAverageDebtByClient(String clientDocumentNumber) {

        return  creditRepository.findAll().filter(x -> x.getClientDocumentNumber() != null &&
                        x.getClientDocumentNumber().equals(clientDocumentNumber))
                .groupBy(CreditEntity::getClientDocumentNumber)
                .flatMap(a -> a
                        .collectList().map(list ->
                                new CreditDailyReportEntity(a.key(), list.stream().count(),list.stream().collect(Collectors.averagingDouble(CreditEntity::getCurrentDebt)), new Date(), list)))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
}
