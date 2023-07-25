package swm.s3.coclimb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import swm.s3.coclimb.api.adapter.out.persistence.gym.GymJpaRepository;
import swm.s3.coclimb.api.adapter.out.persistence.user.UserJpaRepository;
import swm.s3.coclimb.api.application.service.AuthService;
import swm.s3.coclimb.api.application.service.GymService;
import swm.s3.coclimb.api.application.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupport {
    @Autowired UserService userService;
    @Autowired UserJpaRepository userJpaRepository;
    @Autowired GymService gymService;
    @Autowired GymJpaRepository gymJpaRepository;
    @Autowired AuthService authService;
}
