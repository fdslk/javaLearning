package com.openapi.demo;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import org.junit.jupiter.api.BeforeEach;

public class BaseIntegrationTest {
    @BeforeEach
    void initRestAssured() {
        final OpenApiValidationFilter validationFilter = new OpenApiValidationFilter("/openapi.yaml");
    }
}
