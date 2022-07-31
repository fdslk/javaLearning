package com.openapi.demo;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get a book by its name")
    public Mono<Book> getBookByName(@PathVariable String bookName) {
        return bookService.getBookByName(bookName);
    }

    @GetMapping("/filter")
    @Operation(summary = "Get book list by price range")
    public Mono<ResponseEntity> filterBooks(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
        return bookService.getBookByPriceRange(minPrice, maxPrice)
                .collectList().flatMap(
                        books -> books.size() == 0 ?
                                Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null))
                                : Mono.just(ResponseEntity.ok(books))
                );
    }
}
