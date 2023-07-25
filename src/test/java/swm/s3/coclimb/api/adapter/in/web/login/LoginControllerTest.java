package swm.s3.coclimb.api.adapter.in.web.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramOAuthRecord;
import swm.s3.coclimb.api.application.port.in.login.LoginCommand;
import swm.s3.coclimb.config.WebConfig;
import swm.s3.coclimb.interceptor.AutoLoginInterceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@ActiveProfiles("test")
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InstagramOAuthRecord instagramOAuthRecord;

    @MockBean
    WebConfig webConfig;

    @MockBean
    AutoLoginInterceptor autoLoginInterceptor;

    @MockBean
    LoginCommand loginCommand;


    @Test
    @DisplayName("인스타그램 로그인 페이지로 리다이렉트 된다.")
    void redirectInstagramLoginPage() throws Exception {
        mockMvc.perform(get("/login/instagram"))
                .andExpect(status().is3xxRedirection())
                .andExpect(
                        result -> result.getResponse().getRedirectedUrl().contains("https://api.instagram.com/oauth/authorize?client_id="))
                .andExpect(
                        result -> result.getResponse().getRedirectedUrl().contains("&redirect_uri="))
                .andExpect(
                        result -> result.getResponse().getRedirectedUrl().contains("&scope=user_profile,user_media&response_type=code"));
    }
}