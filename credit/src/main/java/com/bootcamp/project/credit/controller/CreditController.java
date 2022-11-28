package com.bootcamp.project.credit.controller;

import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/Credit")
public class CreditController {
    @Autowired
    CreditService creditService;

    @GetMapping(value = "/FindOne/{creditNumber}")
    public Mono<CreditEntity> Get_One(@PathVariable("creditNumber") String document){
        return creditService.getOne(document);
    }
    @GetMapping(value = "/FindAll")
    public Flux<CreditEntity> Get_All(){

        return creditService.getAll();
    }
    @PostMapping(value = "/Save")
    public Mono<CreditEntity> Save(@RequestBody CreditEntity col){

        return creditService.save(col);
    }
    @PutMapping(value = "/Update/{creditNumber}/{creditType}")
    public Mono<CreditEntity> Update(@PathVariable("creditNumber") String document,@PathVariable("creditType") String type){
        return creditService.update(document, type);
    }
    @DeleteMapping  (value = "/Delete/{creditNumber}")
    public Mono<Void> Delete(@PathVariable("creditNumber") String creditNumber){
        return creditService.delete(creditNumber);
    }
    @PutMapping(value = "/payDebt/{creditNumber}/{amount}")
    public Mono<CreditEntity> payDebt(@PathVariable("creditNumber") String accountNumber,@PathVariable("amount") double amount){
        return creditService.payDebt(accountNumber,amount);
    }
    @PutMapping(value = "/addCreditCardDebt/{creditNumber}/{amount}")
    public Mono<CreditEntity> addCreditCardDebt(@PathVariable("creditNumber") String accountNumber,@PathVariable("amount") double amount){
        return creditService.addCreditCardDebt(accountNumber,amount);
    }
    @GetMapping(value = "/getCreditCardBalance/{creditNumber}")
    public double getBalance(@PathVariable("creditNumber") String creditNumber){
        return creditService.getBalance(creditNumber);
    }
    @GetMapping(value = "/getCurrentDebt/{creditNumber}")
    public double getCurrentDebt(@PathVariable("creditNumber") String creditNumber){
        return creditService.getCurrentDebt(creditNumber);
    }
}
