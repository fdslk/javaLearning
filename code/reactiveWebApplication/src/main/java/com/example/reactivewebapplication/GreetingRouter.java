package com.example.reactivewebapplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

        return RouterFunctions
                .route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);

    }

    @Bean
    public RouterFunction<ServerResponse> operatorRoute(GreetingHandler greetingHandler){
        return RouterFunctions.route()
                .GET("/divide", RequestPredicates.queryParam("param1", t->true).
                        and(queryParam("param2", t->true)).and(accept(MediaType.APPLICATION_JSON)), greetingHandler::divide).build();
    }
}