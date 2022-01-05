package com.example.demo.router;

import com.example.demo.handler.CityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class CityRouter {
    @Bean
    public RouterFunction<ServerResponse> routeCity(CityHandler cityHandler) {
        return route(GET("/helloHandler").and(accept(MediaType.TEXT_PLAIN)), cityHandler::helloCity);
    }
}
