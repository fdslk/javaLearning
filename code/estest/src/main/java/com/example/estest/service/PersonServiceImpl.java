package com.example.estest.service;

import com.example.estest.controller.model.Person;
import com.example.estest.repository.ClientBeans;
//import com.example.estest.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service("PersonService")
public class PersonServiceImpl implements PersonService {
//public class PersonServiceImpl {
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ClientBeans client;
    @Override
    public boolean index(Person person) {
//        Person save = personRepository.save(person);
//        return null != save.getId();
        return false;
    }

    public boolean indexWithRHLC(Person person) throws IOException {
        IndexRequest request = new IndexRequest("person");
        ObjectMapper objectMapper = new ObjectMapper();
        request.id("1");
        request.source(objectMapper.writeValueAsString(person), XContentType.JSON);
        IndexResponse indexResponse = client.restHighLevelClient().index(request, RequestOptions.DEFAULT);
        if (indexResponse.status().equals(RestStatus.CREATED))
            return true;
        return false;
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
