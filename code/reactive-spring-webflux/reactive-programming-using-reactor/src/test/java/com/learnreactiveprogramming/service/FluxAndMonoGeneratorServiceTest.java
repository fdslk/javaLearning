package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


class FluxAndMonoGeneratorServiceTest {
    private final FluxAndMonoGeneratorService fluxAndMonoGeneratorService;

    public FluxAndMonoGeneratorServiceTest(){
        fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
    }
    @Test
    void givenFlux_WhennameFLuxIsInvoked_ThenReturnFlux(){
        Flux<String> nameFlux = fluxAndMonoGeneratorService.nameFlux();
        StepVerifier.create(nameFlux)
        .expectNext("aaa", "bbb", "ccc")
        .verifyComplete();
    }

    @Test
    void upperCaseNameFlux() {
        Flux<String> nameFlux = fluxAndMonoGeneratorService.upperCaseNameFlux();
        StepVerifier.create(nameFlux)
                .expectNext("AAA", "BBB", "CCC")
                .verifyComplete();
    }

    @Test
    void upperCaseNameFlux_Immutability() {
        Flux<String> nameFlux = fluxAndMonoGeneratorService.upperCaseNameFlux_immutability();
        StepVerifier.create(nameFlux)
                .expectNext("aaa", "bbb", "ccc")
                .verifyComplete();
    }

    @Test
    void upperCaseNameFlux_Filter() {
        Flux<String> nameFlux = fluxAndMonoGeneratorService.upperCaseNameFlux_filter(2);
        StepVerifier.create(nameFlux)
                .expectNext("BBB", "CCC")
                .verifyComplete();
    }

    @Test
    void nameFlux_flatMap() {
        Flux<String> nameFlux = fluxAndMonoGeneratorService.nameFlux_flatMap(2);
        StepVerifier.create(nameFlux)
                .expectNext("B", "B", "B", "C", "C", "C")
                .verifyComplete();
    }

    @Test
    void nameFlux_flatMap_async() {
        Flux<String> nameFlux = fluxAndMonoGeneratorService.nameFlux_flatMapSequential_async(2);
        StepVerifier.create(nameFlux)
                .expectNext("B", "B", "B", "C", "C", "C")
                .verifyComplete();
    }

    @Test
    void nameFlux_concatMap() {
        Flux<String> nameFlux = fluxAndMonoGeneratorService.nameFlux_concatMap(2);
        StepVerifier.create(nameFlux)
                .expectNext("B", "B", "B", "C", "C", "C")
                .verifyComplete();
    }
}