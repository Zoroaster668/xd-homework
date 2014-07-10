package xdhomework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:es-store.xml", "test-context.xml"})
public class ElasticsearchSinkModuleTest {

    @Autowired
    @Qualifier("elasticSearchTestTemplate")
    private ElasticsearchTemplate elasticsearchTemplate;

    @Value("${index:twitterIndex}")
    private String index;

    /**
     * Handy to verify I've got things set up correctly, talking to correct cluster, etc.
     *
     * To create test index, assuming default ES settings:
     *  curl -XPUT http://localhost:9200/testindex
     *
     * @throws Exception if problems etc. etc.
     */
    @Test
    public void canConnectToES() throws Exception {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("testindex").build();
        long count = elasticsearchTemplate.count(searchQuery);
        assertEquals("there are zero items in test index", count, 0);
    }

    /**
     * Asserts that _something_ was writting to the specified ES index.
     * @throws Exception if problems communicating with ES cluster
     */
    @Test
    public void testTweetSearch() throws Exception {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices(index).build();
        assertNotEquals("more than zero items in index", elasticsearchTemplate.count(searchQuery), 0);
    }
}
