package swm.s3.coclimb.api.docs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import swm.s3.coclimb.api.RestDocsTestSupport;
import swm.s3.coclimb.domain.document.GymDocument;
import swm.s3.coclimb.domain.document.UserDocument;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchControllerDocsTest extends RestDocsTestSupport {

    @Test
    @DisplayName("키워드와 검색 대상을 입력하면 자동완성된 검색 결과를 제공하는 API")
    void autoCompleteName() throws Exception {
        // given
        List<String> gymNames = readFileToList("src/test/resources/docker/elastic/gyms.txt");

        for (String gymName : gymNames) {
            gymDocumentRepository.save(GymDocument.builder()
                            .name(gymName)
                    .build());
        }
        userDocumentRepository.save(UserDocument.builder()
                .name("더클서울맨")
                .build());

        String keyword = "더클서울";
        Integer target1 = 1;
        Integer target2 = 0;
        // when, then
        ResultActions result = mockMvc.perform(get("/search/autocomplete")
                        .queryParam("keyword", keyword)
                        .queryParam("targets", String.valueOf(target1))
                        .queryParam("targets", String.valueOf(target2))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("더클서울맨"))
                .andExpect(jsonPath("$[0].type").value(target1))
                .andExpect(jsonPath("$[1].name").value("더클라임 클라이밍 짐앤샵 서울대점"))
                .andExpect(jsonPath("$[1].type").value(target2));

        // docs
        result.andDo(document("search-autocomplete-name",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                        parameterWithName("keyword").description("검색 키워드"),
                        parameterWithName("targets").description("검색 대상 타입(0:암장, 1:유저) \r\n(여러개 가능)")
                ),
                responseFields(
                        fieldWithPath("[].name").type(JsonFieldType.STRING)
                                .description("자동완성 이름 결과"),
                        fieldWithPath("[].type").type(JsonFieldType.NUMBER)
                                .description("자동완성 결과 타입")
                )));
    }

}