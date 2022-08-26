package com.example.esTest.controller.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Person {
    private final String name;
    private final Integer age;
    private final String id;
    private final String job;
}
