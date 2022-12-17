package com.bootcamp.project.credit.controller;

import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.service.CreditService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(CreditController.class)
public class CreditControllerImplementationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreditService creditService;

    @Test
    public void save()
    {
        CreditEntity OE = new CreditEntity();
        Mono<CreditEntity> MTest = Mono.just(OE);
        when(creditService.save(OE)).thenReturn(MTest);
        webTestClient.post().uri("/Credit/Save")
                .body(Mono.just(MTest),CreditEntity.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void update()
    {
        CreditEntity OE = new CreditEntity();
        Mono<CreditEntity> MTest = Mono.just(OE);
        when(creditService.update("ABC",100)).thenReturn(MTest);
        webTestClient.put().uri("/Credit/Update/ABC/400")
                .body(Mono.just(MTest),CreditEntity.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void delete()
    {
        given(creditService.delete(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/Credit/Delete/ABC")
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void getOne()
    {
        CreditEntity OE = new CreditEntity(null,"AAA",null,null,null,null,null,null,0,0,null,null,null);
        Mono<CreditEntity> MTest = Mono.just(OE);
        when(creditService.getOne(any())).thenReturn(MTest);
        Flux<CreditEntity> responseBody = webTestClient.get().uri("/Credit/FindOne/AAA")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CreditEntity.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getCreditNumber().equals("AAA"))
                .verifyComplete();
    }
    @Test
    public void getAll()
    {
        CreditEntity OE = new CreditEntity(null,"AAA",null,null,null,null,null,null,0,0,null,null,null);
        CreditEntity OE2 = new CreditEntity(null,"BBB",null,null,null,null,null,null,0,0,null,null,null);
        Flux<CreditEntity> MTest = Flux.just(OE,OE2);
        when(creditService.getAll()).thenReturn(MTest);
        Flux<CreditEntity> responseBody = webTestClient.get().uri("/Credit/FindAll")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CreditEntity.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(OE)
                .expectNext(OE2)
                .verifyComplete();
    }
}
