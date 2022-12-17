package com.bootcamp.project.credit.controller;

import com.bootcamp.project.credit.entity.report.CreditDailyReportEntity;
import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.entity.report.CreditReportEntity;
import com.bootcamp.project.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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
    @PostMapping(value = "/RegisterPersonal")
    public Mono<CreditEntity> registerPersonalCredit(@RequestBody CreditEntity col){
        return creditService.registerPersonalCredit(col);
    }
    @PostMapping(value = "/RegisterBusiness")
    public Mono<CreditEntity> registerCompanyCredit(@RequestBody CreditEntity col){
        return creditService.registerCompanyCredit(col);
    }
    @PutMapping(value = "/PayCredit/{creditNumber}/{amount}")
    public Mono<CreditEntity> payCredit(@PathVariable("creditNumber") String accountNumber,@PathVariable("amount") double amount){
        return creditService.payCredit(accountNumber,amount);
    }
    @PutMapping(value = "/AddCreditCardConsume/{creditNumber}/{amount}")
    public Mono<CreditEntity> addCreditCardConsume(@PathVariable("creditNumber") String accountNumber,@PathVariable("amount") double amount){
        return creditService.addCreditCardConsume(accountNumber,amount);
    }
    @GetMapping(value = "/GetByClient/{clientDocumentNumber}")
    public Flux<CreditEntity> getByClient(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.getByClient(clientDocumentNumber);
    }
    @GetMapping(value = "/GetCurrentDebt/{creditNumber}")
    public Mono<Double> getCurrentDebt(@PathVariable("creditNumber") String creditNumber){
        return creditService.getCurrentDebt(creditNumber);
    }
    @GetMapping(value = "/GetCreditCardAvailableBalance/{creditNumber}")
    public Mono<Double> getCreditCardAvailableBalance(@PathVariable("creditNumber") String creditNumber){
        return creditService.getCreditCardAvailableBalance(creditNumber);
    }
    @GetMapping(value = "/CheckDueDebtByClient/{clientDocumentNumber}")
    public Mono<Boolean> checkDueDebtByClient(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.checkDueDebtByClient(clientDocumentNumber);
    }
    @GetMapping(value = "/GetCreditsByDates/{initialDate}/{finalDate}")
    public Flux<CreditReportEntity> getCreditsByDates(@PathVariable("initialDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date initialDate, @PathVariable("finalDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date finalDate){
        return creditService.getCreditsByDates(initialDate,finalDate);
    }
    @GetMapping(value = "/GetCreditsByClient/{clientDocumentNumber}")
    public Flux<CreditReportEntity> getCreditsByClient(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.getCreditsByClient(clientDocumentNumber);
    }
    @GetMapping(value = "/GetAverageDebtByClient/{clientDocumentNumber}")
    public Flux<CreditDailyReportEntity> getAverageDebtByClient(@PathVariable("clientDocumentNumber") String clientDocumentNumber){
        return creditService.getAverageDebtByClient(clientDocumentNumber);
    }
}
