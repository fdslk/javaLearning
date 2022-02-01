package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class CombinationPublisherUtilTests {
    private final CombinationPublisherUtil combinationPublisherUtil;
    private final Flux<Integer> oddNumbers;
    private final Flux<Integer> evenNumbers;

    public CombinationPublisherUtilTests() {
        combinationPublisherUtil = new CombinationPublisherUtil();
        evenNumbers = combinationPublisherUtil.createEvenNumbers(0, 5);
        oddNumbers = combinationPublisherUtil.createOddNumbers(0, 5);
    }

    @Test
    void shouldReturnOddNumberListWhenCallCreateOddNumbers() {
        Flux<Integer> oddNumbers = combinationPublisherUtil.createOddNumbers(0, 10);

        StepVerifier
                .create(oddNumbers)
                .assertNext(t -> assertThat(t).isEqualTo(1))
                .expectNext(3)
                .expectNext(5)
                .expectNext(7)
                .expectNext(9)
                .expectComplete()
                .log()
                .verify();
    }

    @Test
    void givenFluxes_whenConcatIsInvoked_ThenConcat(){
        Flux<Integer> integerFlux = evenNumbers.concatWith(oddNumbers);
//        StepVerifier.create(Flux.concat(evenNumbers, oddNumbers))
        StepVerifier.create(integerFlux)
                .assertNext(t->assertThat(t).isEqualTo(0))
                .assertNext(t->assertThat(t).isEqualTo(2))
                .assertNext(t->assertThat(t).isEqualTo(4))
                .assertNext(t->assertThat(t).isEqualTo(1))
                .assertNext(t->assertThat(t).isEqualTo(3))
                .verifyComplete();
    }

    @Test
    void givenFluxes_whenCombineLastedIsInvoked_thenCombineLasted(){
        StepVerifier.create(Flux.combineLatest(evenNumbers, oddNumbers, (a, b) -> a + b))
                .assertNext(t -> assertThat(t).isEqualTo(5))
                .assertNext(t -> assertThat(t).isEqualTo(7))
                .verifyComplete();
    }

    @Test
    void givenFluxes_WhenMergeIsInvoked_thenMerge(){
        StepVerifier.create(Flux.merge(evenNumbers, oddNumbers))
                .assertNext(t->assertThat(t).isEqualTo(0))
                .assertNext(t->assertThat(t).isEqualTo(2))
                .assertNext(t->assertThat(t).isEqualTo(4))
                .assertNext(t->assertThat(t).isEqualTo(1))
                .assertNext(t->assertThat(t).isEqualTo(3))
                .verifyComplete();
    }

    @Test
    public void givenFluxes_whenZipIsInvoked_thenZip() {
        Flux<Integer> fluxOfIntegers = Flux.zip(
                evenNumbers,
                oddNumbers,
                (a, b) -> a + b);

        StepVerifier.create(fluxOfIntegers)
                .expectNext(1) // 0 + 1
                .expectNext(5) // 2 + 3
                .expectComplete()
                .verify();
    }

    @Test
    public void givenMono_whenZipIsInvoked_thenZip() {
        Mono<String> firstMono = Mono.just("one");
        Mono<String> secondMono = Mono.just("two");

        StepVerifier.create(Mono.zip(firstMono, secondMono, (a, b) -> a +b))
                .expectNext("onetwo") // 0 + 1
                .expectComplete()
                .verify();
    }
}
