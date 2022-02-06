package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReduceTests {
    @Test
    void GivenFlux_WhenReduceIsInvoke_ThenReturnMappers(){
        ArrayList<Mapper> mappers = new ArrayList<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        for (Integer i:
             numbers) {
            Mapper mapper = new Mapper();
            mapper.setElement(i.toString());
            mappers.add(mapper);
        }
        Mono<ArrayList<Mapper>> arrayListMono = Flux.just("t").reduce(mappers, (merge, value) -> {
            Mapper mapper = new Mapper();
            mapper.setElement(value);
            mappers.add(mapper);
            return mappers;
        });
        StepVerifier
                .create(arrayListMono)
                .assertNext(t -> {
                    Integer index = 1;
                    for (Mapper mapper: t) {
                        if(index != 7)
                            assertThat(mapper.getElement()).isEqualTo(index.toString());
                        else
                            assertThat(mapper.getElement()).isEqualTo("t");
                        index ++;
                    }})
                .expectComplete().verify();

    }
}
