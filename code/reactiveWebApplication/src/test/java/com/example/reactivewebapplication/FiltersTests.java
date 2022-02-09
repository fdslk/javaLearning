package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
public class FiltersTests {
    @Test
    void GivenFluxes_WhenFilterIsInvoked_ThenGetNoElement(){
        Flux<Integer> filter = Flux.just(1).filter(t -> t > 0 && t != 1);
        StepVerifier.create(filter)
                .expectComplete().verify();
    }

    @Test
    void GivenFluxes_WhenFilterIsInvoked_ThenGetMoreThenOneElement(){
        Flux<Integer> just = Flux.just(1, 2, 3, 4).filter(t -> t > 0 && t != 1);
        StepVerifier.create(just)
                .assertNext(t -> {
                            assertThat(t).isEqualTo(2);
                        }
                )
                .assertNext(t->assertThat(t).isEqualTo(3))
                .assertNext(t->assertThat(t).isEqualTo(4))
                .expectComplete().verify();
    }
}
