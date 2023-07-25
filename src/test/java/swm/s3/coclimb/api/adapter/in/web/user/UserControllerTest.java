package swm.s3.coclimb.api.adapter.in.web.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.adapter.out.persistence.user.UserJpaRepository;
import swm.s3.coclimb.config.WebConfig;
import swm.s3.coclimb.domain.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserJpaRepository userJpaRepository;

    @MockBean
    WebConfig webConfig;

    @Test
    @DisplayName("/users/me 으로 접속하면 세션 정보 기반으로 유저를 조회한다.")
    void getUserBySessionData() throws Exception {
        // given
        Long instagramUserId = 123456789L;
        String username = "username";
        userJpaRepository.save(User.builder()
                .username(username)
                .instagramUserId(instagramUserId)
                .build());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("instagramUserId", instagramUserId);

        // when
        mockMvc.perform(get("/users/me").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.instagramUserId").value(instagramUserId));
    }
}