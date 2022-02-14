package com.learnreactiveprogramming.service;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    @Test
    void namesMono_flatMap() {
        Mono<List<String>> listMono = fluxAndMonoGeneratorService.namesMono_flatMap(2);
        StepVerifier.create(listMono)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        Flux<String> listMono = fluxAndMonoGeneratorService.namesMono_flatMapMany(2);
        StepVerifier.create(listMono)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }
}