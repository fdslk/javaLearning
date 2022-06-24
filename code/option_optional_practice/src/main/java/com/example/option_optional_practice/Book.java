package com.example.option_optional_practice;

import lombok.Data;

import java.util.Optional;

@Data
public class Book {
    String title;
    Optional<String> subTitle;
}
