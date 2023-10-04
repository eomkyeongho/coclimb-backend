package swm.s3.coclimb.api.adapter.out.persistence.gym;

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


}
