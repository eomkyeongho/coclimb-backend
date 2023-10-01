package swm.s3.coclimb.api.adapter.out.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ElasticsearchClientManager {

    private final ElasticsearchClient esClient;

    public ElasticsearchClient getEsClient() {
        return esClient;
    }

    protected Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> createSearchQuery(String index, String field, String keyword, int size) {
        return s -> s.index(index)
                .source(c -> c
                        .filter(f -> f
                                .includes(field)
                        )
                )
                .size(size)
                .query(q -> q
                        .multiMatch(t -> t
                                .fields(field+".nori", field+".ngram")
                                .query(keyword)
                        )
                );
    }

    protected Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> createExactSearchQuery(String index, String field, String keyword, int size) {
        return s -> s.index(index)
                .source(c -> c
                        .filter(f -> f
                                .excludes("created_at")
                                .excludes("last_modified_at")
                                .excludes("@timestamp")
                                .excludes("@version")
                        )
                )
                .size(size)
                .query(q -> q
                        .matchPhrase(t->t
                                .field(field+".keyword")
                                .query(keyword))
                );
    }

}
