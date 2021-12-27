package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RestController
public class TestController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/helloBaidu")
    public Mono<String> SayHelloToBaidu(){
        var webClient = WebClient.builder().baseUrl("http://localhost:9999/baidu.com").build();
        Mono<String> result = webClient.get().retrieve().bodyToMono(String.class);
        return result;
    }
}
