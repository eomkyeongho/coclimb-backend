package swm.s3.coclimb.api.docs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import swm.s3.coclimb.api.RestDocsTestSupport;
import swm.s3.coclimb.domain.User;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerDocsTest extends RestDocsTestSupport {


    @Test
    @DisplayName("/users/me 으로 접속하면 세션 정보 기반으로 유저를 조회한다.")
    void getUserBySessionData() throws Exception {
        // given
        Long instagramUserId = 123456789L;
        String username = "username";
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("instagramUserId", instagramUserId);
        given(userQuery.getUserByInstagramUserId(instagramUserId))
                .willReturn(User.builder()
                .username(username)
                .instagramUserId(instagramUserId)
                .build());
        // when, then
        ResultActions result = mockMvc.perform(get("/users/me")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.instagramUserId").value(instagramUserId));

        // docs
        result.andDo(document("user-read",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("username").type(JsonFieldType.STRING)
                                .description("사용자이름"),
                        fieldWithPath("instagramUserId").type(JsonFieldType.NUMBER)
                                .description("인스타그램 사용자 아이디")
                )));
    }

}