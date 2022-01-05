package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller
@RestController
public class TestController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/helloBaidu")
    public Mono<String> SayHelloToBaidu(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:9090/baidu.com").build();
        Mono<String> result = webClient.get().retrieve().bodyToMono(String.class);
        return result;
    }

    @GetMapping("/divide")
    public Mono<String> AddOperation(@RequestParam(value = "param1") Integer param1, @RequestParam(value = "param2") Integer param2) {

        if(param2 == 0){
            return Mono.<String>error(IOException::new)
                    .onErrorReturn("Error");
        }
        Mono<String> result = Mono.just(String.valueOf(param1/param2));
        return result;
    }
}
