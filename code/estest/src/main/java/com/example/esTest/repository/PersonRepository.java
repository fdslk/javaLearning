package com.example.esTest.repository;

import com.example.esTest.controller.model.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {
    List<Person> findByName(String name);
}
