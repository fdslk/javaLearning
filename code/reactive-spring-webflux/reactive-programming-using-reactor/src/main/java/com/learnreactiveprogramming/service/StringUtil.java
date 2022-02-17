package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class StringUtil {
    public Flux<String> nameMono_flatMap(){
        return Flux.just("alex")
                .map(String::toUpperCase);
    }

    public Function<Throwable, Mono<String>> onErrorResumeTestFunction(){
        return (throwable) -> {
            if(throwable instanceof Exception)
                return Mono.just("0");
            return Mono.just("1");
        };
    }
}
