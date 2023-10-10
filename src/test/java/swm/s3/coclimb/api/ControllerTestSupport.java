package swm.s3.coclimb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.adapter.in.web.gym.GymController;
import swm.s3.coclimb.api.adapter.in.web.login.LoginController;
import swm.s3.coclimb.api.adapter.in.web.media.MediaController;
import swm.s3.coclimb.api.adapter.in.web.report.ReportController;
import swm.s3.coclimb.api.adapter.in.web.search.SearchController;
import swm.s3.coclimb.api.adapter.in.web.user.UserController;
import swm.s3.coclimb.api.adapter.out.oauth.instagram.InstagramOAuthRecord;
import swm.s3.coclimb.api.adapter.out.oauth.kakao.KakaoOAuthRecord;
import swm.s3.coclimb.api.application.port.in.gym.GymCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymQuery;
import swm.s3.coclimb.api.application.port.in.login.LoginCommand;
import swm.s3.coclimb.api.application.port.in.media.MediaCommand;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;
import swm.s3.coclimb.api.application.port.in.report.ReportCommand;
import swm.s3.coclimb.api.application.port.in.report.ReportQuery;
import swm.s3.coclimb.api.application.port.in.search.SearchQuery;
import swm.s3.coclimb.api.application.port.in.user.UserCommand;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserLoadPort;
import swm.s3.coclimb.config.argumentresolver.LoginUserArgumentResolver;
import swm.s3.coclimb.config.security.JwtManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = {
        GymController.class,
        UserController.class,
        LoginController.class,
        MediaController.class,
        ReportController.class,
        SearchController.class
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
    @MockBean
    protected UserCommand userCommand;
    @MockBean
    protected UserLoadPort userLoadPort;

    // Login
    @MockBean
    protected LoginCommand loginCommand;

    // Instagram
    @MockBean
    protected InstagramOAuthRecord instagramOAuthRecord;

    // Kakao
    @MockBean
    protected KakaoOAuthRecord kakaoOAuthRecord;

    // Media
    @MockBean
    protected MediaCommand mediaCommand;
    @MockBean
    protected MediaQuery mediaQuery;

    // Report
    @MockBean
    protected ReportCommand reportCommand;
    @MockBean
    protected ReportQuery reportQuery;

    // Security
    @MockBean
    protected JwtManager jwtManager;

    @MockBean
    protected LoginUserArgumentResolver loginUserArgumentResolver;

    // Search
    @MockBean
    protected SearchQuery searchQuery;

    protected List<String> readFileToList(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
