package com.example.estest.controller.model;

import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;

@Data
//@Document(indexName = "person")
public class Person {
    private final String name;
    private final Integer age;
//    @Id
    private final String id;
    private final String job;

    public Person(final String name, final Integer age, final String id, final String job) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.job = job;
    }
}
