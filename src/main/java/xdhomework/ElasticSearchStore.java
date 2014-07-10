package xdhomework;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

public class ElasticSearchStore {

    private ElasticsearchTemplate template;
    private String index;

    public ElasticSearchStore(){}

    public void store(String payload){
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndexName(index)
                .withObject(payload)
                .build();
        template.index(indexQuery);
    }

    public ElasticsearchTemplate getTemplate(){ return template; }
    public void setTemplate(ElasticsearchTemplate template){ this.template = template; }

    public String getIndex(){ return index; }
    public void setIndex(String index){ this.index = index; }

}
