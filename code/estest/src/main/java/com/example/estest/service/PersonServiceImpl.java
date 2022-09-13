package com.example.estest.service;

import com.example.estest.controller.model.Person;
import com.example.estest.repository.ClientBeans;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service("PersonService")
public class PersonServiceImpl implements PersonService {
//public class PersonServiceImpl {
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final Log log = LogFactory.getLog("PersonService");

    @Autowired
    private ClientBeans client;
    @Override
    public boolean index(Person person) {
//        Person save = personRepository.save(person);
//        return null != save.getId();
        return false;
    }

    public boolean indexWithRHLC(Person person) throws IOException {
        IndexRequest request = new IndexRequest("person", "doc", java.util.UUID.randomUUID().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        request.source(objectMapper.writeValueAsString(person), XContentType.JSON);
        IndexResponse indexResponse = client.restHighLevelClient().index(request, RequestOptions.DEFAULT);
        log.info(indexResponse.getResult().toString());
        if (indexResponse.status().equals(RestStatus.OK))
            return true;
        return false;
    }

    @Override
    public Person findPersonWithRHLC(String name) throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("name", name));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.restHighLevelClient().search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();

        return Arrays.stream(hits.getHits()).findFirst().map(x -> {
            try {
                log.info(x.getSourceAsString());
                return new ObjectMapper().readValue(x.getSourceAsString(), Person.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.info(e.getOriginalMessage());
                return null;
            }
        }).orElseGet(() -> new Person("default", 18, "1234", "worker"));
    }

    @Override
    public List<Person> findByName(String name) {
//        return personRepository.findByName(name);
        return null;
    }

//    @Override
    public Person findPersonByName(String name) {
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchQuery("name", name).minimumShouldMatch("75%"))
//                .build();
//        return Optional.ofNullable(elasticsearchRestTemplate.search(searchQuery, Person.class, IndexCoordinates.of("person")))
//                .map(SearchHits::getSearchHits)
//                .map(x -> x.size() > 0 ? x.get(0).getContent(): null)
//                .orElseGet(() -> new Person("default", 18, "1234", "worker"));
        return null;
    }
}
