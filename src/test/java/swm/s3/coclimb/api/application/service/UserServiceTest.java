package swm.s3.coclimb.api.application.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramRestApiManager;
import swm.s3.coclimb.api.adapter.out.persistence.user.UserJpaRepository;
import swm.s3.coclimb.api.application.port.out.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.user.UserUpdatePort;
import swm.s3.coclimb.api.exception.errortype.user.UserNotFound;
import swm.s3.coclimb.domain.User;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Transactional
class UserServiceTest extends IntegrationTestSupport {
    @Autowired UserService userService;
    @Mock
    InstagramRestApiManager instagramRestApiManager;
    @Autowired
    UserLoadPort userLoadPort;
    @Autowired
    UserUpdatePort userUpdatePort;
    @Autowired
    UserJpaRepository userJpaRepository;


    @AfterEach
    void tearDown() {
        userJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("인스타그램의 User Id로 사용자 정보를 조회한다.")
    void getUserByInstagramUserId() throws Exception {
        // given
        long instagramUserId = 1L;
        userJpaRepository.save(User.builder()
                .username("유저이름")
                .instagramUserId(instagramUserId)
                .build());
        // when
        User sut = userService.getUserByInstagramUserId(instagramUserId);

        // then
        Assertions.assertThat(sut)
                .extracting("username", "instagramUserId")
                .containsExactly("유저이름", 1L);
    }

    @Test
    @DisplayName("존재하지 않는 유저 정보를 조회하면 UserNotFound에러가 발생한다.")
    void getUserNotExistedByInstagramUserId() throws Exception {
        // given
        long instagramUserId = 1L;
        userJpaRepository.save(User.builder()
                .username("유저이름")
                .instagramUserId(instagramUserId)
                .build());
        // when, then
        assertThatThrownBy(() -> userService.getUserByInstagramUserId(instagramUserId + 1))
                .isInstanceOf(UserNotFound.class);

    }



}
