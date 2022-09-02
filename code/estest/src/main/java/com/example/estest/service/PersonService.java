package com.example.estest.service;

import com.example.estest.controller.model.Person;

import java.io.IOException;
import java.util.List;

public interface PersonService {
    boolean index(Person person);
    boolean indexWithRHLC(Person person) throws IOException;

    List<Person> findByName(String name);

    Person findPersonByName(String name);
}
