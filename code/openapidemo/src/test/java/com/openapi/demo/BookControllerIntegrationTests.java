package com.openapi.demo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;


public class BookControllerIntegrationTests extends BaseIntegrationTest {
    @Test
    void shouldGetValidBookSchema() {
        when()
                .get("/book/test")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
