package com.example.reactivewebapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(100)
public class LogFilter implements WebFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.info("left: 111");
        return chain.filter(exchange)
                .doOnSuccess(aVoid -> logger.info("right: 111"))
                .doOnError(RuntimeException.class, e -> logger.error("error1: {}", e.getMessage()));
      }
}
