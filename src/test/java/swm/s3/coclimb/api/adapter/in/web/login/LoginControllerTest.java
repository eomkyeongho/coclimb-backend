package swm.s3.coclimb.api.adapter.in.web.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.ControllerTestSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("/login/instagram 으로 접속하면 인스타그램 로그인 페이지로 리다이렉트 된다.")
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