package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.report.CreditDailyReportEntity;
import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.entity.report.CreditReportEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface CreditService {

    public Mono<CreditEntity> getOne(String creditNumber);
    public Flux<CreditEntity> getAll();
    public Mono<CreditEntity> save(CreditEntity colEnt);
    public Mono<CreditEntity> update(String creditNumber, double currentDebt);
    public Mono<Void> delete(String creditNumber);
    public Mono<CreditEntity> registerPersonalCredit(CreditEntity colEnt);
    public Mono<CreditEntity> registerCompanyCredit(CreditEntity colEnt);
    public Mono<CreditEntity> payCredit(String creditNumber, double amount);
    public Mono<CreditEntity> addCreditCardConsume(String creditNumber, double amount);
    public Flux<CreditEntity> getByClient(String clientDocumentNumber);
    public Mono<Double> getCurrentDebt(String creditNumber);
    public Mono<Double> getCreditCardAvailableBalance(String creditNumber);
    public Mono<Boolean> checkDueDebtByClient(String clientDocumentNumber);
    public Flux<CreditReportEntity> getCreditsByDates(Date initialDate, Date finalDate);
    public Flux<CreditReportEntity> getCreditsByClient(String clientDocumentNumber);
    Flux<CreditDailyReportEntity> getAverageDebtByClient(String clientDocumentNumber);
}
