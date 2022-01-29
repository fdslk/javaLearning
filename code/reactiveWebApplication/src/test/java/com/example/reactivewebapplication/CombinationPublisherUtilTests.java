package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class CombinationPublisherUtilTests {
    private final CombinationPublisherUtil combinationPublisherUtil;

    public CombinationPublisherUtilTests() {
        combinationPublisherUtil = new CombinationPublisherUtil();
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
        Flux<Integer> oddNumbers = combinationPublisherUtil.createOddNumbers(0, 5);
        Flux<Integer> evenNumbers = combinationPublisherUtil.createEvenNumbers(0, 5);
        StepVerifier.create(Flux.concat(evenNumbers, oddNumbers))
                .assertNext(t->assertThat(t).isEqualTo(0))
                .assertNext(t->assertThat(t).isEqualTo(2))
                .assertNext(t->assertThat(t).isEqualTo(4))
                .assertNext(t->assertThat(t).isEqualTo(1))
                .assertNext(t->assertThat(t).isEqualTo(3))
                .verifyComplete();
    }
}
