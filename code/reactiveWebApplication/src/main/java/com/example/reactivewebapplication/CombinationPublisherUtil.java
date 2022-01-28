package com.example.reactivewebapplication;

import reactor.core.publisher.Flux;

public class CombinationPublisherUtil {
    public Flux<Integer> createOddNumbers(Integer min, Integer max){
        return Flux.range(min, max).filter(x->x % 2 > 0);
    }


    public Flux<Integer> createEvenNumbers(Integer min, Integer max){
        return Flux.range(min, max).filter(x->x % 2 == 0);
    }
}
