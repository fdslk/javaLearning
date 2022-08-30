package com.example.estest.service;

import com.example.estest.controller.model.Person;

import java.util.List;

public interface PersonService {
    boolean index(Person person);

    List<Person> findByName(String name);

    Person findPersonByName(String name);
}
