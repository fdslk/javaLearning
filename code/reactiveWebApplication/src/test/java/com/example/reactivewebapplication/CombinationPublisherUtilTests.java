package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CombinationPublisherUtilTests {
    @Test
    void shouldReturnOddNumberListWhenCallCreateOddNumbers() {
        CombinationPublisherUtil combinationPublisherUtil = new CombinationPublisherUtil();
        Flux<Integer> oddNumbers = combinationPublisherUtil.createOddNumbers(0, 10);

        StepVerifier
                .create(oddNumbers)
                .expectNext(1)
                .expectNext(3)
                .expectNext(5)
                .expectNext(7)
                .expectNext(9)
                .expectComplete()
                .log()
                .verify();
    }
}
