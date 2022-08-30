package com.example.estest;

import junit.framework.TestCase;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;
import static org.elasticsearch.client.RequestOptions.DEFAULT;

public class BaseIntegrationTest extends TestCase {
    protected static final ElasticsearchClusterRunner clusterRunner = clusterRunner();

    @Autowired
    protected RestHighLevelClient restHighLevelClient;

    public static ElasticsearchClusterRunner clusterRunner() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ElasticsearchClusterRunner elasticsearchClusterRunner = new ElasticsearchClusterRunner();
        //create ES node
        elasticsearchClusterRunner.onBuild((number, settingsBuilder) -> {})
                .build(newConfigs()
                        .basePath("./target/es")
                        .numOfNode(1)
                        .disableESLogger());
        // wait for yellow status

        elasticsearchClusterRunner.ensureYellow();
        return elasticsearchClusterRunner;
    }

    @Before
    public void setUp() throws Exception{
        createEsIndex();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    protected void createEsIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("person");
        createIndexRequest.source("catalogue/search_index_mapping.json", XContentType.JSON);
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
}