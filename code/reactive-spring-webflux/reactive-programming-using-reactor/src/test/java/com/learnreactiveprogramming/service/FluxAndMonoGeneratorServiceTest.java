package com.learnreactiveprogramming.service;

import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {
    private final FluxAndMonoGeneratorService fluxAndMonoGeneratorService;

    private final StringUtil stringUtilMock;


    public FluxAndMonoGeneratorServiceTest(){
        stringUtilMock = Mockito.mock(StringUtil.class);
        fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService(stringUtilMock);
    }

    @BeforeEach
    public void initTest(){
        org.mockito.Mockito.when(stringUtilMock.nameMono_flatMap()).thenReturn(Flux.empty());
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

    @Test
    void nameFlux_transforms() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.nameFlux_transforms(2);
        StepVerifier.create(stringFlux)
                .expectNext("B", "B", "B", "C", "C", "C")
                .verifyComplete();
    }

    @Test
    void nameFlux_transforms_filterMoreThanMaxLength() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.nameFlux_transforms(6);
        StepVerifier.create(stringFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void test_Mock(){
        org.mockito.Mockito.when(stringUtilMock.nameMono_flatMap()).thenReturn(Flux.just("bob"));
        StepVerifier.create(fluxAndMonoGeneratorService.test())
                .expectNext("bob")
               .verifyComplete();
    }

    @Test
    void nameFlux_transforms_switchIfEmpty() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.nameFlux_transforms_switchIfEmpty(6);
        StepVerifier.create(stringFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.explore_concat();
        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C","D")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.explore_merge();
        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C","D")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.explore_mergeSequential();
        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C","D")
                .verifyComplete();
    }

    @Test
    void fluxOnErrorResume() {
        org.mockito.Mockito.when(stringUtilMock.onErrorResumeTestFunction()).thenReturn((t) -> Mono.just("1"));
        Mono<String> stringFlux = fluxAndMonoGeneratorService.fluxOnErrorResume(1, 0);
        StepVerifier.create(stringFlux.log())
                .expectNext("1")
                .verifyComplete();
    }
}