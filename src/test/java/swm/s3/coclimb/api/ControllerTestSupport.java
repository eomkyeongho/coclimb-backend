package swm.s3.coclimb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.adapter.in.web.auth.AuthController;
import swm.s3.coclimb.api.adapter.in.web.gym.GymController;
import swm.s3.coclimb.api.adapter.in.web.login.LoginController;
import swm.s3.coclimb.api.adapter.in.web.user.UserController;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramOAuthRecord;
import swm.s3.coclimb.api.application.port.in.auth.AuthCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymQuery;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.config.WebConfig;
import swm.s3.coclimb.interceptor.AutoLoginInterceptor;

@WebMvcTest(controllers = {
        GymController.class,
        UserController.class,
        LoginController.class,
        AuthController.class
})
@ActiveProfiles("test")
public abstract class ControllerTestSupport{
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
    // Gym
    @MockBean
    protected GymCommand gymCommand;
    @MockBean
    protected GymQuery gymQuery;

    // User
    @MockBean
    protected UserQuery userQuery;

    // Auth
    @MockBean
    protected AuthCommand authCommand;
    @MockBean
    protected AutoLoginInterceptor autoLoginInterceptor;

    // Instagram
    @MockBean
    InstagramOAuthRecord instagramOAuthRecord;

    @MockBean
    protected WebConfig webConfig;
}
