package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TestControllerTests {

    @Autowired
    private WebTestClient testClient;
    @ParameterizedTest
    @CsvSource({"'',Hello World!", "Tony,Hello Tony!"})
    public void ShouldReturnExpectedResultWhenCallHelloWithParameters(String param, String expectedResult){

        var url = String.format("/hello?myName=%s",param);

        var result = this.testClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .returnResult();
        Assertions.	assertEquals(result.getResponseBody(), expectedResult);
    }
}
