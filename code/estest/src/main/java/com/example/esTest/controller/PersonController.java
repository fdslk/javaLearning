package com.example.esTest.controller;

import com.example.esTest.controller.model.Person;
import com.example.esTest.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Person addNewPerson(@RequestBody final Person person) {
        if (personService.index(person))
            return person;
        return null;
    }

    @GetMapping
    public Person queryPersonByName(@RequestParam String name) {
        return personService.findByName(name).stream().findFirst().orElseGet(() -> new Person("default", 18, "1234", "worker"));
    }
}
