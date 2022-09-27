import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.elasticsearch.test.ESIntegTestCase.*;

@ClusterScope(scope = Scope.SUITE)
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@RunWith(com.carrotsearch.randomizedtesting.RandomizedRunner.class)
public class EsIntegrationTest extends ESIntegTestCase {
    @Test
    public void test() throws Exception {
        createIndex("test-index");
        assertTrue(indexExists("test-index"));
    }
}
