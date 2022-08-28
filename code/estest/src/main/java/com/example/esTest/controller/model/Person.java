package com.example.esTest.controller.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@RequiredArgsConstructor
@Data
@Document(indexName = "person")
public class Person {
    private final String name;
    private final Integer age;
    @Id
    private final String id;
    private final String job;
}
