package swm.s3.coclimb.api.adapter.out.persistence.gym;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import swm.s3.coclimb.domain.document.GymDocument;

import java.util.Optional;

public interface GymDocumentRepository extends ElasticsearchRepository<GymDocument, String>, GymDocumentRepositoryCustom {

    Optional<GymDocument> findByName(String name);
}
