package swm.s3.coclimb.api.adapter.in.web.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import swm.s3.coclimb.api.ControllerTestSupport;
import swm.s3.coclimb.api.application.service.UserService;
import swm.s3.coclimb.domain.user.InstagramUserInfo;
import swm.s3.coclimb.domain.user.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {

    @Mock
    UserService userService;

    @Test
    @DisplayName("토큰 정보 기반으로 유저를 조회한다.")
    void getUserByToken() throws Exception {
        // given
        String accessToken = "token";
        given(jwtManager.getSubject(accessToken)).willReturn("1");
        given(userLoadPort.getById(1L)).willReturn(User.builder()
                .name("username")
                .instagramUserInfo(InstagramUserInfo.builder().build())
                .build());
        // when, then
        mockMvc.perform(get("/users/me")
                        .header("Authorization",accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    @DisplayName("토큰 정보 기반으로 유저를 삭제한다.")
    void deleteUserByToken() throws Exception {
        //given
        String accessToken = "token";
        given(jwtManager.getSubject(accessToken)).willReturn("1");
        given(userLoadPort.getById(1L)).willReturn(User.builder()
                .name("username")
                .instagramUserInfo(InstagramUserInfo.builder().build())
                .build());
        willDoNothing().given(userCommand).deleteUser(any(User.class));

        //when
        //then
        mockMvc.perform(delete("/users/me")
                        .header("Authorization",accessToken))
                .andExpect(status().isNoContent());
        then(userCommand).should(times(1)).deleteUser(any(User.class));
    }
}