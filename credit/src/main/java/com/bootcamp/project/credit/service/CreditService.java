package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.CreditEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    /*
CRUD BÁSICO
*/
    public Mono<CreditEntity> getOne(String creditNumber);
    public Flux<CreditEntity> getAll();
    public Mono<CreditEntity> save(CreditEntity colEnt);
    public Mono<CreditEntity> update(String creditNumber, String creditType);
    public Mono<Void> delete(String creditNumber);
    public Mono<CreditEntity> payDebt(String creditNumber, double amount);
    public Mono<CreditEntity> addCreditCardDebt(String creditNumber, double amount);
    public double getCurrentDebt(String creditNumber);
    public double getBalance(String creditNumber);
}
