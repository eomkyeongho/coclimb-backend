package swm.s3.coclimb.api.adapter.in.web.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import swm.s3.coclimb.api.ControllerTestSupport;
import swm.s3.coclimb.domain.User;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {


    @Test
    @DisplayName("세션 정보 기반으로 유저를 조회한다.")
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
        mockMvc.perform(get("/users/me")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.instagramUserId").value(instagramUserId));
    }
}