package com.esjavaapi.demo;

import java.io.IOException;

public interface PersonService {
    boolean indexWithJavaApi(Person person) throws IOException;
    boolean indexWithRHLC(Person person) throws IOException;
}
