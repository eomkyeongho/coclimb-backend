package swm.s3.coclimb.learning.elasticsearch;

import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import swm.s3.coclimb.api.IntegrationTestSupport;

import java.util.Optional;

public class DataElasticsearchLearningTest extends IntegrationTestSupport {


    @Autowired
    TestEmployeeRepository employeeRepository;
    @Document(indexName = "employee")
    @Getter
    public class Employee {
        @Id
        private String id;
        private String name;
        private String department;

        public Employee(String id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
        }
    }

    @Test
    @DisplayName("")
    void test() throws Exception {
        // given
        Employee employee = new Employee("1", "name", "department");

        // when
        Employee sut = employeeRepository.save(employee);
        // then
        Optional<Employee> out = employeeRepository.findById(sut.getId());
        Assertions.assertThat(out.get().getName()).isEqualTo("name");
    }

}
