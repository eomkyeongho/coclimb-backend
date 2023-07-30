package swm.s3.coclimb.api.application.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.domain.user.InstagramInfo;
import swm.s3.coclimb.domain.user.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest extends IntegrationTestSupport {

    @AfterEach
    void tearDown(){
        userJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("InstagramUserId 로 사용자를 조회한다.")
    void findUserByInstagramUserId() throws Exception {
        // given
        userJpaRepository.save(User.builder()
                .name("user")
                .instagramInfo(InstagramInfo.builder()
                        .userId(1L)
                        .build())
                .build());
        // when
        Optional<User> sut = userService.findUserByInstagramUserId(1L);

        // then
        assertThat(sut).isNotNull();
        assertThat(sut.get())
                .extracting("name", "instagramInfo.userId")
                .containsExactly("user", 1L);
    }

    @Test
    @DisplayName("인스타그램의 정보를 사용하여 사용자를 생성한다.")
    void createUserByInstagramInfo() throws Exception {
        // given
        InstagramInfo instagramInfo = InstagramInfo.builder()
                .userId(1L)
                .build();

        // when
        Long userId = userService.createUserByInstagramInfo(instagramInfo);
        User sut = userJpaRepository.findById(userId).orElse(null);

        // then
        assertThat(userId).isNotNull();
        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isNotEmpty();
        assertThat(sut.getInstagramInfo())
                .hasFieldOrPropertyWithValue("userId",1L);
    }
}