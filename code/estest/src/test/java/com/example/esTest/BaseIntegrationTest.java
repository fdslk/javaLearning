package com.example.estest;

import lombok.extern.slf4j.Slf4j;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
                        .baseHttpPort(9200)
                        .disableESLogger());
        // wait for yellow status

        elasticsearchClusterRunner.ensureYellow();
        return elasticsearchClusterRunner;
    }

    @Before
    public void setUp() throws Exception{
        log.info("cluster runner starting");
        createEsIndex();
    }

    @After
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
}