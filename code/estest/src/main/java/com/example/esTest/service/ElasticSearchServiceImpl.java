package com.example.esTest.service;

import com.example.esTest.controller.model.Person;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
    @Override
    public boolean index(Person person) {
        return false;
    }
}
