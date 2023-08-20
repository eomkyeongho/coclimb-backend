package swm.s3.coclimb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.adapter.out.persistence.gym.GymJpaRepository;
import swm.s3.coclimb.api.adapter.out.persistence.gymlike.GymLikeJpaRepository;
import swm.s3.coclimb.api.adapter.out.persistence.media.MediaJpaRepository;
import swm.s3.coclimb.api.adapter.out.persistence.user.UserJpaRepository;
import swm.s3.coclimb.config.security.JwtManager;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
public abstract class RestDocsTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected JwtManager jwtManager;
    @Autowired
    protected UserJpaRepository userJpaRepository;
    @Autowired
    protected MediaJpaRepository mediaJpaRepository;
    @Autowired
    protected GymJpaRepository gymJpaRepository;
    @Autowired
    protected GymLikeJpaRepository gymLikeJpaRepository;

    @AfterEach
    void clearDB() {
        gymLikeJpaRepository.deleteAll();
        mediaJpaRepository.deleteAll();
        gymJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
