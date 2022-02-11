package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class FluxAndMonoGeneratorService {

    public Flux<String> nameFlux() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("ccc");
        return Flux.fromIterable(strings);
    }
    public Mono<String> nameMono() {
        return Mono.just("ddd");
    }
    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.nameFlux()
        .subscribe(name -> {
            System.out.println(name);
        });

        fluxAndMonoGeneratorService.nameMono()
        .subscribe(name -> {
            System.out.println(name);
        });
    }
}
