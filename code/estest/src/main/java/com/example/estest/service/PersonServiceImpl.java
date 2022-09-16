package com.example.estest.service;

import com.example.estest.controller.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service("PersonService")
public class PersonServiceImpl implements PersonService {
//public class PersonServiceImpl {
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Log log = LogFactory.getLog("PersonService");
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Person defaultPerson = new Person("default", 18, "1234", "worker");

    @Autowired
    private RestHighLevelClient restHighLevelClient;
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
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info(indexResponse.getResult().toString());
        if (indexResponse.status().equals(RestStatus.CREATED))
            return true;
        return false;
    }

    @Override
    public Person findPersonWithRHLC(String name) throws IOException {
        SearchResponse searchResponse = restHighLevelClient.search(searchRequestBuilder(name), RequestOptions.DEFAULT);

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
    public Option<Person> findPersonWithRHLCAsync(String name) {
        return Option.of(new SearchResponseCallable()).map(task -> {
            Future<SearchResponse> future = executor.submit(task);
            restHighLevelClient
                    .searchAsync(searchRequestBuilder(name), RequestOptions.DEFAULT,
                            new SearchResponseActionListener(task));
            return Try.of(future::get).toOption()
                    .map(SearchResponse::getHits)
                    .map(SearchHits::getHits)
                    .map(x -> Arrays.stream(x).findFirst())
                    .filter(Optional::isPresent)
                    .map(x -> {
                        try {
                            return mapper.readValue(x.get().getSourceAsString(), Person.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
        }).map(x -> x.getOrElse(defaultPerson));
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

    private static class SearchResponseActionListener implements ActionListener<SearchResponse> {
        private SearchResponseCallable searchResponseCallable;

        public SearchResponseActionListener(SearchResponseCallable searchResponseCallable) {
            this.searchResponseCallable = searchResponseCallable;
        }

        @Override
        public void onResponse(SearchResponse searchResponse) {
            this.searchResponseCallable.setSearchResponse(searchResponse);
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    private static class SearchResponseCallable implements Callable {

        @Setter
        private volatile SearchResponse searchResponse;

        @Override
        public SearchResponse call() {
            while(null == searchResponse);
            return searchResponse;
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
