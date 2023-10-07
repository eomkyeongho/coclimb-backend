package swm.s3.coclimb.api.adapter.out.persistence.user;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import swm.s3.coclimb.domain.document.UserDocument;

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {
}
