package com.learnreactiveprogramming.service;

import io.vavr.collection.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    private final StringUtil stringUtil;

    public FluxAndMonoGeneratorService(StringUtil stringUtil) {
         this.stringUtil = stringUtil;
    }

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

    public Flux<String> upperCaseNameFlux(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("ccc");
        return Flux.fromIterable(strings).map(String::toUpperCase).log();
    }

    public Flux<String> upperCaseNameFlux_immutability(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("ccc");
        Flux<String> stringFlux = Flux.fromIterable(strings);
        stringFlux.map(String::toUpperCase);
        return stringFlux;
    }

    public Flux<String> upperCaseNameFlux_filter(int stringLength){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aa");
        strings.add("bbb");
        strings.add("ccc");
        return Flux.fromIterable(strings)
                .filter(s -> s.length() > stringLength)
                .map(String::toUpperCase);
    }

    public Flux<String> nameFlux_flatMap(int stringLength){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aa");
        strings.add("bbb");
        strings.add("ccc");
        return Flux.fromIterable(strings)
                .filter(s -> s.length() > stringLength)
                .map(String::toUpperCase)
                .flatMap(s -> splitName(s));
    }

    public Flux<String> nameFlux_transforms(int stringLength){
        List<String> of = List.of("aa", "bbb", "ccc");

        Function<Flux<String>, Flux<String>> filterMap = name -> name.filter(s -> s.length() > stringLength)
                .map(String::toUpperCase);

        return Flux.fromIterable(of)
                .transform(filterMap)
                .flatMap(s -> splitName(s))
                .defaultIfEmpty("default");
    }

    public Flux<String> nameFlux_transforms_switchIfEmpty(int stringLength){
        List<String> of = List.of("aa", "bbb", "ccc");

        Function<Flux<String>, Flux<String>> filterMap = name -> name
                .filter(s -> s.length() > stringLength)
                .map(String::toUpperCase)
                .flatMap(s -> splitName(s));

        Flux<String> aDefault = Flux.just("default")
                .transform(filterMap);

        return Flux.fromIterable(of)
                .transform(filterMap)
                .switchIfEmpty(aDefault);
    }

    public Flux<String> nameFlux_concatMap(int stringLength){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aa");
        strings.add("bbb");
        strings.add("ccc");
        return Flux.fromIterable(strings)
                .filter(s -> s.length() > stringLength)
                .map(String::toUpperCase)
                .concatMap(s -> splitName(s));
    }

    public Flux<String> nameFlux_flatMapSequential_async(int stringLength){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aa");
        strings.add("bbb");
        strings.add("ccc");
        return Flux.fromIterable(strings)
                .filter(s -> s.length() > stringLength)
                .map(String::toUpperCase)
                .flatMapSequential(s -> splitName_withDelay(s)).log();
    }

    public Mono<io.vavr.collection.List<String>> namesMono_flatMap(int stringLength){
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono);
    }

    public Flux<String> namesMono_flatMapMany(int stringLength){
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitName);
    }

    public Flux<String> test(){
        return stringUtil.nameMono_flatMap();
    }

    public Flux<String> explore_concat(){
        Flux<String> just = Flux.just("A", "B");
        Flux<String> just1 = Flux.just("C", "D");
        return just.concatWith(just1);
    }

    public Flux<String> explore_concat_mono(){
        Flux<String> just = Flux.just("A");
        Mono<String> just1 = Mono.just("C");
        return just.concatWith(just1);
    }

    public Flux<String> explore_merge(){
        Flux<String> stringFlux = Flux.just("A", "B")
                .delayElements(Duration.ofMillis(100));
        Flux<String> stringFlux1 = Flux.just("C", "D")
                .delayElements(Duration.ofMillis(210));
        return Flux.merge(stringFlux, stringFlux1).log();
    }

    public Flux<String> explore_mergeSequential(){
        Flux<String> stringFlux = Flux.just("A", "B")
                .delayElements(Duration.ofMillis(100));
        Flux<String> stringFlux1 = Flux.just("C", "D")
                .delayElements(Duration.ofMillis(10));
        return Flux.mergeSequential(stringFlux, stringFlux1).log();
    }

    public Flux<String> explore_zip() {
        Flux<String> stringFlux = Flux.just("A", "B");
        Flux<String> stringFlux1 = Flux.just("C", "D");
//        return Flux.zip(stringFlux, stringFlux1, (first, second) -> first + second);
        return Flux.zip(stringFlux, stringFlux1)
                .map(t2 -> t2.getT1() + t2.getT2());//zip return tuple, therefore, we can use tuple operator like getT1, getT2;
    }

    public Mono<String> fluxOnErrorResume(int a, int b){
        return divide(a, b)
//                .onErrorResume(t -> stringUtil.onErrorResumeTestFunction().apply(t));
                .onErrorResume(stringUtil.onErrorResumeTestFunction());
//                .onErrorResume((throwable)->{
//                    if(throwable instanceof ArithmeticException){
//                        return Mono.just("1");
//                    }
//                    return Mono.just("0");
//                });
    }

    public Mono<String> divide(int a, int b) {
        return Mono.fromCallable(() -> String.valueOf(a / b));
    }

    //TODO: https://stackoverflow.com/questions/67857350/project-reactor-what-are-differences-between-flux-concat-flux-mergesequential
    private Mono<io.vavr.collection.List<String>> splitStringMono(String s) {
        String[] split = s.split("");
        io.vavr.collection.List<String> of = io.vavr.collection.List.of(split);
        return Mono.just(of);
    }

    private Flux<String> splitName(String s) {
        String[] split = s.split("");
        return Flux.fromArray(split);
    }

    private Flux<String> splitName_withDelay(String s) {
        String[] split = s.split("");
        int i = new Random().nextInt(1000);
        return Flux.fromArray(split)
                .delayElements(Duration.ofMillis(i));
    }

//    public static void main(String[] args) {
//        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
//        fluxAndMonoGeneratorService.nameFlux()
//        .subscribe(name -> {
//            System.out.println(name);
//        });
//
//        fluxAndMonoGeneratorService.nameMono()
//        .subscribe(name -> {
//            System.out.println(name);
//        });
//    }
}
