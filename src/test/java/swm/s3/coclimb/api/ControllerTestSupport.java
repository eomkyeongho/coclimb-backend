package swm.s3.coclimb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.adapter.in.web.auth.AuthController;
import swm.s3.coclimb.api.adapter.in.web.gym.GymController;
import swm.s3.coclimb.api.adapter.in.web.login.LoginController;
import swm.s3.coclimb.api.adapter.in.web.user.UserController;

@WebMvcTest(controllers = {
        GymController.class,
        UserController.class,
        LoginController.class,
        AuthController.class
})
@ActiveProfiles("test")
public abstract class ControllerTestSupport extends MockMvcSupport{
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
