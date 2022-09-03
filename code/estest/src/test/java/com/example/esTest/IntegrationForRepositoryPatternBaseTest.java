package com.example.estest;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 *
 * The following docker command can be used: docker run -d --name es762 -p
 * 9200:9200 -e "discovery.type=single-node" elasticsearch:7.8.0
 */

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationForRepositoryPatternBaseTest {
    /*
    1.provide request client
    2.provide es client
    3.provide mock or document operation for arranging the requirement data
    */
    private RestHighLevelClient client = null;

    @LocalServerPort
    private int port;
    private final String TEST_HOSTS = "http://localhost:";
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void setUp() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        client = RestClients.create(clientConfiguration)
                .rest();
    }

    protected void indexData() throws IOException {
        String personJsonData = "{\"name\":\"Bill\",\"age\":10,\"id\":\"1\",\"job\": \"driver\"}";
        String personJsonData2 = "{\"name\":\"Billy\",\"age\":10,\"id\":\"2\",\"job\": \"driver\"}";
        List<String> personsJsonDataList = Arrays.asList(personJsonData, personJsonData2);
        IndexRequest indexRequest = new IndexRequest("person");
        personsJsonDataList.stream().forEach(personJson -> {
            indexRequest.source(personJson, XContentType.JSON);
            try {
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("index failure: %s", personJson);
            }
        });
    }

    protected String getRequest(String queryPath) {
        return this.restTemplate.getForObject(TEST_HOSTS + port + "/" + queryPath, String.class);
    }
}
