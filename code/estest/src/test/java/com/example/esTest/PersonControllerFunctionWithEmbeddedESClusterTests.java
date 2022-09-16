package com.example.estest;

import com.example.estest.controller.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerFunctionWithEmbeddedESClusterTests extends BaseIntegrationTest {
    @Test
    void shouldGetPersonByRHLC() throws JsonProcessingException {

        String request = getRequest("person/rhlc?name=Bill");
        assertThat(new ObjectMapper().readValue(request, Person.class).getName()).isEqualTo("Bill");
    }

    @Test
    void shouldGetDataFromEmbeddedES() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        assertThat(searchResponse.getHits().getTotalHits()).isEqualTo(2);
    }
}
