package com.customstarter.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ValueController {

    @Autowired
    private TimeoutProperties timeoutProperties;

    @GetMapping("/value")
    public Mono<String> getValue(){
        return Mono.just(timeoutProperties.getProduction());
    }
}
