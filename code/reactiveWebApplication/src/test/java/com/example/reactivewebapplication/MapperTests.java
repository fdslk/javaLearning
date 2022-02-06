package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTests {
    @Test
    void GiveFlux_WhenMapIsInvoked_ThenMap(){
        Function<String, String> mapper = String::toUpperCase;
        Flux<String> inFlux = Flux.just("baeldung", ".", "com").map(mapper);
        StepVerifier.create(inFlux)
                .expectNext("BAELDUNG", ".", "COM")
                .verifyComplete();
    }

    @Test
    void GiveFlux_WhenFlatMapIsInvoked_ThenFlatMap(){
        Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase().split(""));
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<String> outFlux = inFlux.flatMap(mapper);
        outFlux.filter(t -> t != ".").toIterable().forEach(System.out::println);
        ArrayList<String> objects = new ArrayList<>();
        outFlux.subscribe(objects::add);
        StepVerifier.create(outFlux)
                .expectNext("B")
                .expectNext("A")
                .expectNext("E")
                .expectNext("L")
                .expectNext("D")
                .expectNext("U")
                .expectNext("N")
                .expectNext("G")
                .expectNext(".")
                .expectNext("C")
                .expectNext("O")
                .expectNext("M")
                .verifyComplete();
        assertThat(objects).containsExactlyInAnyOrder("B", "A", "E", "L", "D", "U", "N", "G", ".", "C", "O", "M");
    }

    @Test
    void GivenFlux_WhenMapToMapperIsInvoked_ThenGetMapperList(){
        Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase().split(""));
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<Mapper> outFlux = inFlux.flatMap(mapper).map(t -> {
            Mapper mapperEntity = new Mapper();
            mapperEntity.setElement(t);
            return mapperEntity;
        });

        outFlux.toIterable().forEach(t -> System.out.println(t.getElement()));
        StepVerifier.create(outFlux)
                .assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("B");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("A");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("E");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("L");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("D");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("U");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("N");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("G");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo(".");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("C");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("O");
                }).assertNext(t -> {
                    assertThat(t.getElement()).isEqualTo("M");
                })
                .expectComplete()
                .log()
                .verify();
    }
}
