package com.example.esTest.controller;

import com.example.esTest.controller.model.Person;
import com.example.esTest.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Person addNewPerson(@RequestBody final Person person) {
        if (elasticSearchService.index(person))
            return person;
        return null;
    }
}
