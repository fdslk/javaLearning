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
        .expectNext("aaa", "bbb", "cccc")
        .verifyComplete();
    }

}