package com.example.esTest.service;

import com.example.esTest.controller.model.Person;

import java.util.List;

public interface PersonService {
    boolean index(Person person);

    List<Person> findByName(String name);

    Person findPersonByName(String name);
}
