package com.example.demo;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }
    public static void main(String[] args) {
//        System.exit(SpringApplication.exit(SpringApplication.run(DemoApplication.class, args)));

        SpringApplication.run(DemoApplication.class, args);
    }

}
