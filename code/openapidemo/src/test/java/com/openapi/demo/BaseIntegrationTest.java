package com.openapi.demo;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import io.restassured.RestAssured;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;

import static java.text.MessageFormat.format;

@Log4j2
public class BaseIntegrationTest {

    private static final String BASE_URI = "http://localhost:8080";

    @BeforeEach
    void initRestAssured() {
        final OpenApiValidationFilter validationFilter = new CustomValidationFilter("/openapi.yaml");
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(validationFilter);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static class CustomValidationFilter extends OpenApiValidationFilter {

        public CustomValidationFilter(String specUrlOrDefinition) {
            super(specUrlOrDefinition);
        }

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            log.info(format("[{0}]request from: {1}",requestSpec.getMethod(), requestSpec.getBaseUri() + requestSpec.getDerivedPath()));
            return super.filter(requestSpec, responseSpec, ctx);
        }
    }
}
