package com.openapi.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private final String name;
    private final Integer price;
    private final String author;
}
