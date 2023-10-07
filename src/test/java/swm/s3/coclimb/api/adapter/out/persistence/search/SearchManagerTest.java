package swm.s3.coclimb.api.adapter.out.persistence.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.persistence.search.dto.AutoCompleteNameDto;
import swm.s3.coclimb.api.exception.errortype.internal.ClassNotFound;
import swm.s3.coclimb.domain.document.Document;
import swm.s3.coclimb.domain.document.GymDocument;
import swm.s3.coclimb.domain.document.UserDocument;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SearchManagerTest extends IntegrationTestSupport {

    @Test
    @DisplayName("키워드와 검색 대상 Document를 입력하면 자동완성 결과를 조회한다.")
    void autoCompleteName() {
        // given
        List<String> gymNames = readFileToList("src/test/resources/docker/elastic/gyms.txt");
        for (String gymName : gymNames) {
            gymDocumentRepository.save(GymDocument.builder()
                    .name(gymName)
                    .build());
        }
        List<Document> documents = List.of(Document.GYM);
        String keyword = "더클";
        int size = 10;

        // when
        List<AutoCompleteNameDto> sut = searchManager.autoCompleteName(keyword, documents, size);

        // then
        assertThat(sut).isNotEmpty();
        assertThat(sut.get(0).getNames().get(0)).startsWith("더클");
    }

    @Test
    @DisplayName("키워드와 여러 검색 대상 Document를 입력하면 각 검색 대상별 자동완성 결과를 조횧한다.")
    void autoCompleteNameWithMultiTargets() {
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
        List<AutoCompleteNameDto> sut = searchManager.autoCompleteName(keyword, documents, size);

        // then
        assertThat(sut.size()).isEqualTo(2);
        assertThat(sut).extracting(AutoCompleteNameDto::getIndex)
                .containsExactlyInAnyOrder(
                        Document.GYM.getIndex(),
                        Document.USER.getIndex());
        assertThat(sut.get(1).getNames().get(0)).isEqualTo("더클빨강맨");
    }

    @Test
    @DisplayName("이름으로 자동완성 요청 시, Document에 해당하는 class를 찾을 수 없으면 예외가 발생한다.")
    void autoCompleteNameFailWithWrongDocument() throws Exception {
        // given
        String keyword = "더클";

        List<Document> documents = List.of(Document.NONE);
        int size = 10;

        // when, then
        assertThatThrownBy(() -> searchManager.autoCompleteName(keyword, documents, size))
                .isInstanceOf(ClassNotFound.class);
    }


}