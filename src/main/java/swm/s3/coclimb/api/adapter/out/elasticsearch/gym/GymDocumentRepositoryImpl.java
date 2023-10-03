package swm.s3.coclimb.api.adapter.out.elasticsearch.gym;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import swm.s3.coclimb.api.adapter.out.elasticsearch.ElasticsearchQueryFactory;
import swm.s3.coclimb.api.exception.errortype.elasticsearch.InvalidElasticQuery;
import swm.s3.coclimb.domain.gym.GymDocument;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class GymDocumentRepositoryImpl implements GymDocumentRepositoryCustom {

    private final ElasticsearchClient esClient;
    private final ElasticsearchQueryFactory queryFactory;
    @Override
    public List<String> autoCompleteName(String keyword, int size) {
        try {
            return esClient.search(
                    queryFactory.createSearchQuery("gyms", "name", keyword, size),
                    GymDocument.class)
                    .hits().hits().stream()
                    .map(h -> h.source().getName())
                    .toList();
        } catch (IOException e) {
            throw new InvalidElasticQuery();
        }

    }
}
