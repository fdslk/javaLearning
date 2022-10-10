package com.esjavaapi.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("/rhlc")
    @ResponseStatus(HttpStatus.OK)
    public Person addNewPersonInRHLC(@RequestBody final Person person) throws IOException {
        if (personService.indexWithRHLC(person))
            return person;
        return null;
    }

    @PostMapping("/esc")
    @ResponseStatus(HttpStatus.OK)
    public Person addNewPersonInESC(@RequestBody final Person person) throws IOException {
        if (personService.indexWithJavaApi(person))
            return person;
        return null;
    }
}
