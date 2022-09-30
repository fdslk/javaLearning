import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

public class FunctionalTest extends EmbeddedESBaseTest{
    @Test
    public void shouldRunEmbeddedES() throws IOException {
        System.out.println(111);

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        HttpHost.create("127.0.0.1:9201")
                )
        );

        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "Bill");
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchResponse person = runner.search("person",null, null, 0, 100);

        System.out.println(person.getHits().getTotalHits());
        System.out.println(searchResponse.getHits().getTotalHits());
    }
}
