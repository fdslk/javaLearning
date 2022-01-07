package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args){

        System.out.println(MyCommandLineRunner.class);
        Arrays.stream(args).forEach(s -> {
            System.out.println(s);
        });
    }
}
