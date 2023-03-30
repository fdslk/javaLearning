package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpStatus.OK;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    private final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private final String ALLOWED_ORIGIN = "*";
    private final String ALLOWED_HEADERS = "Content-Type, Authorization";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (request.getPath().pathWithinApplication().value().equals("/")) {
                response.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
                response.getHeaders().add(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
                response.getHeaders().add(ACCESS_CONTROL_MAX_AGE, "3600");
                response.getHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
                if (request.getMethod() == OPTIONS) {
                    response.setStatusCode(OK);
                    return Mono.empty();
                }
            }

            return chain.filter(exchange);
        };
    }
}
