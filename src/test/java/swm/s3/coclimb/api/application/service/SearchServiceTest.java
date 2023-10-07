package swm.s3.coclimb.api.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.application.port.in.search.dto.SearchNameResult;
import swm.s3.coclimb.domain.document.Document;
import swm.s3.coclimb.domain.document.GymDocument;
import swm.s3.coclimb.domain.document.UserDocument;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchServiceTest extends IntegrationTestSupport {

    @Test
    @DisplayName("키워드와 검색 대상 Document를 입력하면 자동완성 결과를 조회한다.")
    void autoComplete() throws Exception {
        // given
        List<String> gymNames = readFileToList("src/test/resources/docker/elastic/gyms.txt");
        for (String gymName : gymNames) {
            gymDocumentRepository.save(GymDocument.builder()
                    .name(gymName)
                    .build());
        }
        userDocumentRepository.save(UserDocument.builder()
                .id("1")
                .name("더클빨강맨")
                .build());

        String keyword = "더클";
        List<Document> documents = List.of(Document.GYM, Document.USER);
        int size = 10;

        // when
        List<SearchNameResult> sut = searchService.autoComplete(keyword, documents, size);

        // then
        assertThat(sut.size()).isEqualTo(size);
        assertThat(sut.get(0).getName()).startsWith("더클");
        assertThat(sut).extracting(SearchNameResult::getName).containsAnyOf("더클빨강맨");
        assertThat(sut).extracting(SearchNameResult::getType).containsAnyOf(Document.USER.getIndex());
    }

    @Test
    @DisplayName("자동완성 시 검색 대상으로 입력하지 않은 Document에 대해서는 검색을 수행하지 않는다.")
    void autoCompleteWithNoTarget() throws Exception {
        // given
        userDocumentRepository.save(UserDocument.builder()
                .id("1")
                .name("더클빨강맨")
                .build());

        String keyword = "더클";
        List<Document> documents = List.of(Document.GYM);
        int size = 10;

        // when
        List<SearchNameResult> sut = searchService.autoComplete(keyword, documents, size);

        // then
        assertThat(sut).isEmpty();
    }
}