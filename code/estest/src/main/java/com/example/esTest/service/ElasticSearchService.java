package com.example.esTest.service;

import com.example.esTest.controller.model.Person;

public interface ElasticSearchService {
    boolean index(Person person);
}
