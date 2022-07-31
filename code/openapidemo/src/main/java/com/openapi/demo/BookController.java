package com.openapi.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{bookName}")
    public Mono<Book> getBookByName(@PathVariable String bookName) {
        return bookService.getBookByName(bookName);
    }

    @GetMapping("/filter")
    public Mono<ResponseEntity> filterBooks(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
        return bookService.getBookByPriceRange(minPrice, maxPrice)
                .collectList().flatMap(
                        books -> books.size() == 0 ?
                                Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null))
                                : Mono.just(ResponseEntity.ok(books))
                );
    }
}
