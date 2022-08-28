package com.example.esTest.service;

import com.example.esTest.controller.model.Person;
import com.example.esTest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
    @Autowired
    private PersonRepository personRepository;
    @Override
    public boolean index(Person person) {
        Person save = personRepository.save(person);
        if (null != save.getId())
            return true;
        return false;
    }
}
