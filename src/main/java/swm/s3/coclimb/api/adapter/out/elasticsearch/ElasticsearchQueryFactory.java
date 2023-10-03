package swm.s3.coclimb.api.adapter.out.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ElasticsearchQueryFactory {

    public Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> createSearchQuery(String index, String field, String keyword, int size) {
        return s -> s.index(index)
                .source(c -> c
                        .filter(f -> f
                                .includes(field)
                        )
                )
                .size(size)
                .query(q -> q
                        .bool(b -> b
                                .should(sh -> sh.matchPhrasePrefix(v -> v
                                        .field(field)
                                        .query(keyword)))
                                .should(sh -> sh.match(v -> v
                                        .field(field + ".nori")
                                        .query(keyword)))
                                .should(sh -> sh.match(v -> v
                                        .field(field + ".edge_ngram")
                                        .query(keyword)))
                                .minimumShouldMatch("1")
                        )
                );
    }
    public Function<Query.Builder, ObjectBuilder<Query>> createSearchQuery2(String index, String field, String keyword, int size) {
        return q -> q
                .bool(b -> b
                        .should(s -> s.matchPhrasePrefix(v -> v
                                .field(field)
                                .query(keyword)))
                        .should(s -> s.match(v -> v
                                .field(field + ".nori")
                                .query(keyword)))
                        .should(s -> s.match(v -> v
                                .field(field + ".edge_ngram")
                                .query(keyword)))
                        .minimumShouldMatch("1")
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
