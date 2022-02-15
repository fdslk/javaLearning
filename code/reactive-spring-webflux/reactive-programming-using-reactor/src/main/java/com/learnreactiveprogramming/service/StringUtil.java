package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;

public class StringUtil {
    public Flux<String> nameMono_flatMap(){
        return Flux.just("alex")
                .map(String::toUpperCase);
    }
}
