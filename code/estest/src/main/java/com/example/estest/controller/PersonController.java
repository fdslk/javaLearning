package com.example.estest.controller;

import com.example.estest.controller.model.Person;
import com.example.estest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/rhlc")
    @ResponseStatus(HttpStatus.OK)
    public Person addNewPersonInRHLC(@RequestBody final Person person) throws IOException {
        if (personService.indexWithRHLC(person))
            return person;
        return null;
    }

    @GetMapping
    public Person queryPersonByName(@RequestParam String name) {
//        return personService.findByName(name).stream().findFirst().orElseGet(() -> new Person("default", 18, "1234", "worker"));
        return personService.findPersonByName(name);
    }

    @GetMapping("/rhlc")
    public Person queryPersonByNameInRHLC(@RequestParam String name) throws IOException {
        return personService.findPersonWithRHLC(name);
    }
}
