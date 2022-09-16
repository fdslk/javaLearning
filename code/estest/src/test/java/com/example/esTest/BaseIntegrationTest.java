package com.example.estest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;
import static org.elasticsearch.client.RequestOptions.DEFAULT;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseIntegrationTest extends BaseTest {
    protected static final ElasticsearchClusterRunner clusterRunner = clusterRunner();

    @Autowired
    protected RestHighLevelClient restHighLevelClient;

    public static ElasticsearchClusterRunner clusterRunner() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ElasticsearchClusterRunner elasticsearchClusterRunner = new ElasticsearchClusterRunner();
        //create ES node
        elasticsearchClusterRunner.onBuild((number, settingsBuilder) -> {
                    settingsBuilder.put("http.cors.enabled", true);
                    settingsBuilder.put("http.cors.allow-origin", "*");
                    settingsBuilder.put("discovery.type", "single-node");
                })
                .build(newConfigs()
                        .basePath("./target/es")
                        .numOfNode(1)
                        .disableESLogger());
        // wait for yellow status

        elasticsearchClusterRunner.ensureYellow();
        return elasticsearchClusterRunner;
    }

    @BeforeAll
    public void setUp() {
        log.info("cluster runner starting");
        indexData();
        clusterRunner.refresh();
    }

    public static RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9202, "http")
        ));
    }

    @AfterAll
    public void tearDown() throws IOException {
        log.info("cluster runner decommission");
        clusterRunner.deleteIndex("person");
        clusterRunner.clean();
        clusterRunner.close();
    }

    protected void createEsIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("person");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, DEFAULT);
        if(createIndexResponse.isAcknowledged()){
            System.out.println("successfully");
        }
        else
        {
            System.out.println("failed");
            System.exit(1);
        }
    }

    protected void indexData() {
        String personJsonData = "{\"name\":\"Bill\",\"age\":10,\"id\":\"1\",\"job\": \"driver\"}";
        String personJsonData2 = "{\"name\":\"Billy\",\"age\":10,\"id\":\"2\",\"job\": \"driver\"}";
        List<String> personsJsonDataList = Arrays.asList(personJsonData, personJsonData2);
        IndexRequest indexRequest = new IndexRequest("person", "doc");
        personsJsonDataList.stream().forEach(personJson -> {
            indexRequest.source(personJson, XContentType.JSON);
            try {
                IndexResponse index = restHighLevelClient.index(indexRequest, DEFAULT);
                if(index.status().equals(RestStatus.CREATED))
                    log.info(String.format("index successfully, :%s", personJson));
                else
                    log.info(String.format("index failed, :%s", personJson));
                restHighLevelClient.indices().refresh(new RefreshRequest("person"), DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(String.format("index failure: %s", personJson), e);
            }
        });
    }

    protected String getRequest(String queryPath) {
        return restTemplate.getForObject(TEST_HOSTS + port + "/" + queryPath, String.class);
    }
}