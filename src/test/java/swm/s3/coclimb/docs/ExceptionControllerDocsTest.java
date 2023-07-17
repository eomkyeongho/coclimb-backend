package swm.s3.coclimb.docs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import swm.s3.coclimb.api.adapter.in.web.gym.GymCreateRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExceptionControllerDocsTest extends RestDocsTestSupport {

    @Test
    @DisplayName("에러 발생 시 수행되는 실패 응답 API")
    void errorResponse() throws Exception {
        // given
        GymCreateRequest request = GymCreateRequest.builder()
                .build();

        // when, then
        ResultActions result = mockMvc.perform(post("/gyms")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value("요청에 유효하지 않은 값이 포함된 필드가 존재합니다."))
                .andExpect(jsonPath("$.fields").isMap())
                .andExpect(jsonPath("$.fields.name").value("암장 이름은 필수입니다."));

        // docs
        result.andDo(document("error-response",
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("요청 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                                .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("메세지"),
                        fieldWithPath("fields").type(JsonFieldType.OBJECT)
                                .description("에러가 발생한 필드 목록"),
                        fieldWithPath("fields.name").type(JsonFieldType.STRING)
                                .description("에러 필드 및 에러 메세지 예시")
                )
        ));
    }

}