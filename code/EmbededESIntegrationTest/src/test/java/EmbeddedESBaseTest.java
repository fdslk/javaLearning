import lombok.extern.slf4j.Slf4j;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;
@Slf4j
public class EmbeddedESBaseTest {
    protected static final ElasticsearchClusterRunner runner = new ElasticsearchClusterRunner();

    @Before
    public void setUp() {
        runner.onBuild((number, settingsBuilder) -> {
            settingsBuilder.put("http.cors.enabled", true);
            settingsBuilder.put("http.cors.allow-origin", "*");
            settingsBuilder.put("discovery.type", "single-node");
        }).build(newConfigs()
                .basePath("./target/es")
                .numOfNode(1)
                .disableESLogger());

        indexData();
        runner.refresh();
    }

    @After
    public void tearDown() throws IOException {
        runner.close();
        runner.clean();
    }

    protected void indexData() {
        String personJsonData = "{\"name\":\"Bill\",\"age\":10,\"id\":\"1\",\"job\": \"driver\"}";
        String personJsonData2 = "{\"name\":\"Billy\",\"age\":10,\"id\":\"2\",\"job\": \"driver\"}";
        List<String> personsJsonDataList = Arrays.asList(personJsonData, personJsonData2);
        personsJsonDataList.stream().forEach(personJson -> {
            IndexResponse index = runner.insert("person", UUID.randomUUID().toString(), personJson);
            if(index.status().equals(RestStatus.CREATED))
                log.info(String.format("index successfully, :%s", personJson));
            else
                log.info(String.format("index failed, :%s", personJson));
        });
    }
}
