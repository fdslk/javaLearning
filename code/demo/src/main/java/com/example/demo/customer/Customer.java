package com.example.demo.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Customer {
    @Value("${name}")
    private String name;

    @Value("true")
    private boolean defaultBoolean;

    @Value("10")
    private int defaultInt;

    @Value("${app.description}")
    private String description;

    public Customer(){
    }

    @PostConstruct
    public void printContent(){
        System.out.println("name = " + name);
        System.out.println("defaultBoolean = " + defaultBoolean);
        System.out.println("defaultInt = " + defaultInt);
        System.out.println("description = " + description);
    }
}
