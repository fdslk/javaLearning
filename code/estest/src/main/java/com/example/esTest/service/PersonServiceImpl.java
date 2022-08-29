package com.example.esTest.service;

import com.example.esTest.controller.model.Person;
import com.example.esTest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Override
    public boolean index(Person person) {
        Person save = personRepository.save(person);
        if (null != save.getId())
            return true;
        return false;
    }

    @Override
    public List<Person> findByName(String name) {
        return personRepository.findByName(name);
    }
}
