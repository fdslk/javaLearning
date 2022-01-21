package com.example.reactivewebapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
@RestController
@RequestMapping("/testrouter")
public class GreetingRouter implements RouterFunction {
    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("RequestMappingHandlerMapping");
    }

    @GetMapping("/test1")
    public Mono<String> test1() {
        return Mono.just("RequestMappingHandlerMapping");
    }
//    @Bean
//    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
//        logger.info("call hello api");
//
//        return RouterFunctions
//                .route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);
//
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> operatorRoute(GreetingHandler greetingHandler){
//        return RouterFunctions.route()
//                .GET("/divide", RequestPredicates.queryParam("param1", t->true).
//                        and(queryParam("param2", t->true)).and(accept(MediaType.APPLICATION_JSON)), greetingHandler::divide).build();
//    }

    @Override
    public Mono<HandlerFunction> route(ServerRequest request) {
        HandlerFunction urlHandler = req -> ServerResponse.ok().bodyValue("RouterFunctionMapping");
        if (request.uri().getPath().equals("/testrouter/test")) {
            return Mono.just(urlHandler);
        } else {
            return Mono.empty();
        }
    }
}