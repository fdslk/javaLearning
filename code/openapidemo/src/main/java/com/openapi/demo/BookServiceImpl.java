package com.openapi.demo;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService{
    private static final List<Book> bookStore = List.of(
            new Book("ten thousand why", 12, "Bill"),
            new Book("ten thousand why1", 13, "Bill"),
            new Book("ten thousand why2", 14, "Bill"),
            new Book("ten thousand why3", 15, "Bill"),
            new Book("ten thousand why4", 16, "Bill"),
            new Book("ten thousand why5", 17, "Bill"),
            new Book("ten thousand why6", 18, "Bill")
    );

    public Mono<Book> getBookByName(String bookName) {
        return Mono.just(new Book("ten thousand why", 12, "Bill"));
    }

    public Flux<Book> getBookByPriceRange(Integer minPrice, Integer maxPrice) {
        return Flux.fromIterable(bookStore.filter(book -> book.getPrice() > minPrice && book.getPrice() < maxPrice));
    }
}
