package com.example.option_optional_practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class BookController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/book")
    public Mono<String> book() throws JsonProcessingException {
        Book book = new Book();
        book.setTitle("Oliver Twist");
        book.setSubTitle(Optional.of("The Parish Boy's Progress"));

        return Mono.just(objectMapper.writeValueAsString(book));
    }
}
