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
    @PutMapping(value = "/Update/{creditNumber}/{currentDebt}")
    public Mono<CreditEntity> Update(@PathVariable("creditNumber") String document,@PathVariable("currentDebt") double currentDebt){
        return creditService.update(document, currentDebt);
    }
    @DeleteMapping  (value = "/Delete/{creditNumber}")
    public Mono<Void> Delete(@PathVariable("creditNumber") String creditNumber){
        return creditService.delete(creditNumber);
    }
    @PutMapping(value = "/PayCredit/{creditNumber}/{amount}")
    public Mono<CreditEntity> payCredit(@PathVariable("creditNumber") String accountNumber,@PathVariable("amount") double amount){
        return creditService.payCredit(accountNumber,amount);
    }
    @PutMapping(value = "/AddCreditCardDebt/{creditNumber}/{amount}")
    public Mono<CreditEntity> addCreditCardDebt(@PathVariable("creditNumber") String accountNumber,@PathVariable("amount") double amount){
        return creditService.addCreditCardDebt(accountNumber,amount);
    }

    @GetMapping(value = "/GetCurrentDebt/{creditNumber}")
    public Mono<Double> getCurrentDebt(@PathVariable("creditNumber") String creditNumber){
        return creditService.getCurrentDebt(creditNumber);
    }
    @GetMapping(value = "/GetByClient/{clientDocumentNumber}")
    public Flux<CreditEntity> getByClient(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.getByClient(clientDocumentNumber);
    }
    @PostMapping(value = "/RegisterPersonal")
    public Mono<CreditEntity> registerPersonalCredit(@RequestBody CreditEntity col){
        return creditService.registerPersonalCredit(col);
    }
    @GetMapping(value = "/GetCreditCardsByClient/{clientDocumentNumber}")
    public Flux<CreditEntity> getCreditCardsByClient(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.getCreditCardsByClient(clientDocumentNumber);
    }
    @PostMapping(value = "/RegisterCompany")
    public Mono<CreditEntity> registerCompanyCredit(@RequestBody CreditEntity col){
        return creditService.registerCompanyCredit(col);
    }
    @GetMapping(value = "/GetAverageDebt/{clientDocumentNumber}")
    public Mono<Double> getAverageDebt(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.getAverageDebt(clientDocumentNumber);
    }
}
