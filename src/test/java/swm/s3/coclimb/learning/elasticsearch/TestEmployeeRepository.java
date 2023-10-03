package swm.s3.coclimb.learning.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import swm.s3.coclimb.learning.elasticsearch.DataElasticsearchLearningTest;

public interface TestEmployeeRepository extends ElasticsearchRepository<DataElasticsearchLearningTest.Employee, String> {
}
