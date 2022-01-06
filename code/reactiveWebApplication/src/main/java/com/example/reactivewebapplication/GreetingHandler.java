package com.example.reactivewebapplication;
import com.sun.media.sound.InvalidDataException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Greeting("Hello, Spring!")));
    }

    public Mono<ServerResponse> divide(ServerRequest serverRequest){
        Optional<String> param1 = serverRequest.queryParam("param1");
        Optional<String> param2 = serverRequest.queryParam("param2");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Integer.parseInt(param1.get())/Integer.parseInt(param2.get())));
    }
}