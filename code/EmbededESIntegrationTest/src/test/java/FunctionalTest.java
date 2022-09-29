import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;

import java.io.IOException;

public class FunctionalTest extends EmbeddedESBaseTest{
    @Test
    public void shouldRunEmbeddedES() throws IOException {
        System.out.println(111);

//        SearchRequest searchRequest = new SearchRequest("person");
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "Bill");
//        searchSourceBuilder.query(matchQueryBuilder);
//        searchRequest.source(searchSourceBuilder);
//
//
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchResponse person = runner.search("person", null, null, 0, 100);

        System.out.println(person.getHits().getTotalHits());
    }
}
