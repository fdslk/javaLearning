package com.example.reactivewebapplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/testrouter")
@Slf4j
public class FilterRest implements RouterFunction {
    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("RequestMappingHandlerMapping");
    }

    @GetMapping("/test1")
    public Mono<Map<String, Object>> test1(ServerWebExchange exchange) {
        Mono<MultiValueMap<String, String>> formData = exchange.getFormData();
        Mono<MultiValueMap<String, Part>> multipartData = exchange.getMultipartData();

        String logPrefix = exchange.getLogPrefix();
        log.info(logPrefix);
        Mono<Map<String, Object>> map = Mono.zip(formData, multipartData)
                .map(tuple -> {
                    Map<String, Object> result = new TreeMap<>();
                    tuple.getT1().forEach((key, values) -> result.put(key, values));
                    tuple.getT2().forEach((key, values) -> result.put(key, values));//TODO: can be serialized
                    return result;
                });

        return map;
//        return Mono.just("RequestMappingHandlerMapping");
    }


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
