package com.example.estest.service;

import com.example.estest.controller.model.Person;
import com.example.estest.repository.ClientBeans;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service("PersonService")
public class PersonServiceImpl implements PersonService {
//public class PersonServiceImpl {
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final Log log = LogFactory.getLog("PersonService");
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Person defaultPerson = new Person("default", 18, "1234", "worker");

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
        if (indexResponse.status().equals(RestStatus.CREATED))
            return true;
        return false;
    }

    @Override
    public Person findPersonWithRHLC(String name) throws IOException {
        SearchResponse searchResponse = client.restHighLevelClient().search(searchRequestBuilder(name), RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();

        return Arrays.stream(hits.getHits()).findFirst().map(x -> {
            try {
                log.info(x.getSourceAsString());
                return mapper.readValue(x.getSourceAsString(), Person.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.info(e.getOriginalMessage());
                return null;
            }
        }).orElse(defaultPerson);
    }

    @Override
    public Optional<Person> findPersonWithRHLCAsync(String name) {
        return Optional.of(Stream.<SearchHit>builder().build()).map(searchHitStream -> {
                    client.restHighLevelClient()
                            .searchAsync(searchRequestBuilder(name), RequestOptions.DEFAULT, new SearchResponseActionListener(searchHitStream));
                    return searchHitStream.findFirst().map(x -> {
                        try {
                            return mapper.readValue(x.getSourceAsString(), Person.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
                })
                .map(person -> person.orElse(defaultPerson));
    }

    private SearchRequest searchRequestBuilder(String name) {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("name", name));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }

    @RequiredArgsConstructor
    private static class SearchResponseActionListener implements ActionListener<SearchResponse> {
        private final Stream<SearchHit> stream;

        @Override
        public void onResponse(SearchResponse searchResponse) {
            Stream<SearchHit> searchHitStream = Arrays.stream(searchResponse.getHits().getHits());
            Stream.concat(stream, searchHitStream);
        }

        @Override
        public void onFailure(Exception e) {

        }
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
