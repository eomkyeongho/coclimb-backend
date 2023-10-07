package swm.s3.coclimb.api.adapter.out.persistence.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.persistence.search.dto.AutoCompleteNameDto;
import swm.s3.coclimb.api.exception.errortype.internal.ClassNotFound;
import swm.s3.coclimb.domain.document.Document;
import swm.s3.coclimb.domain.document.GymDocument;
import swm.s3.coclimb.domain.document.UserDocument;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SearchManagerTest extends IntegrationTestSupport {

    private static Stream<Arguments> autoCompleteName() {
        return Stream.of(
                Arguments.of("락트리 분당", "락트리클라이밍 분당"),
                Arguments.of("더클 서울대", "더클라임 클라이밍 짐앤샵 서울대점"),
                Arguments.of("서울숲 잠실", "서울숲클라이밍 잠실점"),
                Arguments.of("더클B", "더클라임 B 홍대점"),
                Arguments.of("신림 더클라임", "더클라임 클라이밍 짐앤샵 신림점"));
    }
    @ParameterizedTest
    @MethodSource
    @DisplayName("키워드와 검색 대상 Document를 입력하면 자동완성 결과를 조회한다.")
    void autoCompleteName(String keyword, String expected) throws Exception{
        // given
        List<String> gymNames = readFileToList("src/test/resources/docker/elastic/gyms.txt");
        for (String gymName : gymNames) {
            gymDocumentRepository.save(GymDocument.builder()
                    .name(gymName)
                    .build());
        }
        List<Document> documents = List.of(Document.GYM);
        int size = 10;

        // when
        List<AutoCompleteNameDto> sut = searchManager.autoCompleteName(keyword, documents, size);

        // then
        assertThat(sut).isNotEmpty();
        assertThat(sut.get(0).getNames().get(0)).isEqualTo(expected);
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