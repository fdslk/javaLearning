package com.openapi.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Book> getBookByName(String bookName);

    Flux<Book> getBookByPriceRange(Integer minPrice, Integer maxPrice);
}
