package com.esjavaapi.demo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public boolean indexWithJavaApi(Person person) throws IOException {
        co.elastic.clients.elasticsearch.core.IndexResponse indexResponse = elasticsearchClient.index(i -> i
                .index("products")
                .id(UUID.randomUUID().toString())
                .document(person));
        log.info(String.format("%s---%s", indexResponse.result().toString(), person.toString()));
        if (indexResponse.result().equals(Result.Created))
            return true;
        return false;
    }

    @Override
    public boolean indexWithRHLC(Person person) throws IOException {
        IndexRequest request = new IndexRequest("person", "doc", java.util.UUID.randomUUID().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        request.source(objectMapper.writeValueAsString(person), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info(String.format("%s---%s", indexResponse.getResult().toString(), person.toString()));
        if (indexResponse.status().equals(RestStatus.CREATED))
            return true;
        return false;
    }
}
