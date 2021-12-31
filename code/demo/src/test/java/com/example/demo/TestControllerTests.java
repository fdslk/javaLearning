package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class TestControllerTests extends ApiSetup {

    @ParameterizedTest
    @CsvSource({"'',Hello World!", "Tony,Hello Tony!"})
    public void ShouldReturnExpectedResultWhenCallHelloWithParameters(String param, String expectedResult){

        String url = String.format("/hello?myName=%s",param);

//        var result = this.testClient
//                .get()
//                .uri(url)
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectBody(String.class)
//                .returnResult();
//        Assertions.assertEquals(result.getResponseBody(), expectedResult);
    }

    @Test
    public void ShouldReturnCorrectHtmlContentWhenCallHelloBaidu(){
        mockClient.when(request().withPath("/baidu.com"))
                .respond(response().withStatusCode(200).withBody("test"));

//        var result = this.testClient
//                .get()
//                .uri("/helloBaidu")
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectBody(String.class)
//                .returnResult();
//        Assertions.assertEquals(result.getResponseBody(), "test");
    }
}
