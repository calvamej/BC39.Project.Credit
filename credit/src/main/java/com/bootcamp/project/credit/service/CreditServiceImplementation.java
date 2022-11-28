package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.repository.CreditRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CreditServiceImplementation implements CreditService{
    private static Logger Log = Logger.getLogger(CreditServiceImplementation.class);
    @Autowired
    private CreditRepository creditRepository;

    @Override
    public Mono<CreditEntity> getOne(String creditNumber) {
        Log.info("Inicio método getOne.");
        Mono<CreditEntity> col = creditRepository.findAll().filter(x -> x.getCreditNumber().equals(creditNumber)).next();
        return col;
    }

    @Override
    public Flux<CreditEntity> getAll() {
        Log.info("Inicio método getAll.");
        Flux<CreditEntity> col = creditRepository.findAll();
        return col;
    }

    @Override
    public Mono<CreditEntity> save(CreditEntity colEnt) {
        Log.info("Inicio método save.");
        return creditRepository.save(colEnt);
    }

    @Override
    public Mono<CreditEntity> update(String accountNumber, String type) {
        Log.info("Inicio método update.");
        Mono<CreditEntity> col = getOne(accountNumber);
        CreditEntity newCol = col.block();
        newCol.setCreditType(type);
        return creditRepository.save(newCol);
    }

    @Override
    public Mono<Void> delete(String accountNumber) {
        Log.info("Inicio método delete.");
        Mono<CreditEntity> col = getOne(accountNumber);
        CreditEntity newCol = col.block();
        return creditRepository.delete(newCol);
    }
    @Override
    public Mono<CreditEntity> payDebt(String creditNumber, double amount)
    {
        Log.info("Inicio método payDebt.");
        Mono<CreditEntity> col = creditRepository.findAll().filter(x -> x.getCreditNumber().equals(creditNumber)).next();
        CreditEntity newCol = col.block();
        if(newCol==null)
        {
            return Mono.just(new CreditEntity());
        }
        newCol.setCurrentDebt(newCol.getCurrentDebt() - amount);
        return creditRepository.save(newCol);
    }
    @Override
    public Mono<CreditEntity> addCreditCardDebt(String creditNumber, double amount)
    {
        Log.info("Inicio método addCreditCardDebt.");
        Mono<CreditEntity> col = creditRepository.findAll().filter(x -> x.getCreditNumber().equals(creditNumber) && x.getCreditType().equals("TC")).next();
        CreditEntity newCol = col.block();
        if(newCol==null)
        {
            return Mono.just(new CreditEntity());
        }
        if(newCol.getCreditLimit() >= newCol.getCurrentDebt() + amount)
        {
            newCol.setCurrentDebt(newCol.getCurrentDebt() + amount);
            newCol.setTotalDebt(newCol.getTotalDebt() + amount);
            return creditRepository.save(newCol);
        }
        else
        {
            return Mono.just(new CreditEntity());
        }
    }
    @Override
    public double getCurrentDebt(String creditNumber) {
        Log.info("Inicio método getCurrentDebt.");
        Mono<CreditEntity> col = getOne(creditNumber);
        CreditEntity newCol = col.block();
        return newCol.getCurrentDebt();
    }
    @Override
    public double getBalance(String creditNumber) {
        Log.info("Inicio método getBalance.");
        Mono<CreditEntity> col = creditRepository.findAll().filter(x -> x.getCreditNumber().equals(creditNumber) && x.getCreditType().equals("TC")).next();
        CreditEntity newCol = col.block();
        if(newCol==null)
        {
            return 0;
        }
        return newCol.getCreditLimit() - newCol.getCurrentDebt();
    }
}
