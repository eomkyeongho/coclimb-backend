package swm.s3.coclimb.api.adapter.out.persistence.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import swm.s3.coclimb.api.adapter.out.persistence.search.dto.AutoCompleteNameDto;
import swm.s3.coclimb.api.application.port.out.SearchPort;
import swm.s3.coclimb.api.exception.errortype.internal.ClassNotFound;
import swm.s3.coclimb.api.exception.errortype.internal.MethodNotFound;
import swm.s3.coclimb.api.exception.errortype.elasticsearch.InvalidElasticQuery;
import swm.s3.coclimb.domain.document.Document;
import swm.s3.coclimb.domain.document.DocumentClassMap;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SearchManager implements SearchPort {
    private final ElasticsearchClient esClient;
    private final ElasticsearchQueryFactory queryFactory;

    public List<AutoCompleteNameDto> autoCompleteName(String keyword, List<Document> documents, int size) {

        List<AutoCompleteNameDto> results = new ArrayList<>();
            try {
                for (Document document : documents) {
                    Class<?> clazz = Class.forName(DocumentClassMap.getClass(document).getName());
                    List<String> names = esClient.search(
                                    queryFactory.createSearchQuery(document.getIndex(), "name", keyword, size),
                                    clazz)
                            .hits().hits().stream()
                            .map(h -> (String) methodInvoke(clazz, "getName", h.source()))
                            .toList();

                    results.add(AutoCompleteNameDto.builder()
                            .names(names)
                            .index(document.getIndex())
                            .build());
                }

            } catch (IOException e) {
                throw new InvalidElasticQuery();
            } catch (Exception e) {
                throw new ClassNotFound();
            }
        return results;
    }

    private <T> T methodInvoke(Class<?> clazz, String methodName, Object instance) {
        try {
            Method method = clazz.getMethod(methodName);
            return (T) method.invoke(instance);
        } catch (Exception e) {
            throw new MethodNotFound();
        }
    }

}
