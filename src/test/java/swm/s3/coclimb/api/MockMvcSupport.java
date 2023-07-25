package swm.s3.coclimb.api;

import org.springframework.boot.test.mock.mockito.MockBean;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramOAuthRecord;
import swm.s3.coclimb.api.application.port.in.auth.AuthCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymQuery;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.config.WebConfig;
import swm.s3.coclimb.interceptor.AutoLoginInterceptor;

public abstract class MockMvcSupport {

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
