package com.example.reactivewebapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GreetingRouter.class);

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        logger.info("call hello api");

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