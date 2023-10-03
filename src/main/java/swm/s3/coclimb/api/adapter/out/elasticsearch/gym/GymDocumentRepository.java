package swm.s3.coclimb.api.adapter.out.elasticsearch.gym;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import swm.s3.coclimb.domain.gym.GymDocument;

import java.util.Optional;

public interface GymDocumentRepository extends ElasticsearchRepository<GymDocument, String>, GymDocumentRepositoryCustom {

    Optional<GymDocument> findByName(String name);
}
