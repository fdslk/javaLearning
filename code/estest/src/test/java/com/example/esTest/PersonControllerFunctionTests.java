package com.example.estest;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerFunctionTests extends IntegrationForRepositoryPatternBaseTest {
    @Test
    void shouldReturnCorrectPersonEntityWhenGivenPersonNameIsMatching() throws IOException {
        indexData();
        String request = this.getRequest("person?name=Bill");
        assertThat(request).isNotBlank();
    }
}
