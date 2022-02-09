package com.example.reactivewebapplication;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeekTests {
    @Test
    void GivenFluxes_WhenPeekIsInvoke_ThenNoElementChange(){
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        Stream.of("Alice", "Bob", "Chuck").peek(t -> {}).forEach(System.out::println);
    }
}
