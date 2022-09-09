package com.example.estest.controller.model;

import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "person")
public class Person {
    private String name;
    private Integer age;
//    @Id
    private String id;
    private String job;
}
