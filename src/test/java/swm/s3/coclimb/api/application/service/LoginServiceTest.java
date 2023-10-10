package swm.s3.coclimb.api.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.oauth.instagram.InstagramRestApiManager;
import swm.s3.coclimb.api.adapter.out.oauth.instagram.dto.ShortLivedTokenResponse;
import swm.s3.coclimb.domain.user.InstagramUserInfo;
import swm.s3.coclimb.domain.user.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


class LoginServiceTest extends IntegrationTestSupport {

    @MockBean
    InstagramRestApiManager instagramRestApiManager;


    @Test
    @DisplayName("인스타그램으로 첫 로그인 시 새로운 유저를 생성한 뒤 인스타그램 정보를 저장한다.")
    void firstLoginByInstagram(){
        // given
        String code = "12345";
        String token = "token";
        Long instagramUserId = 123L;
        given(instagramRestApiManager.getShortLivedTokenAndUserId(any()))
                .willReturn(new ShortLivedTokenResponse(token, instagramUserId));
        given(instagramRestApiManager.getNewInstagramUserInfo(any()))
                .willReturn(InstagramUserInfo.builder()
                        .id(instagramUserId)
                        .accessToken(token)
                        .build());

        // when
        Long userId = loginService.loginWithInstagram(code);
        Optional<User> sut = userJpaRepository.findById(userId);
        // then
        then(instagramRestApiManager).should().getNewInstagramUserInfo(any());
        assertThat(sut).isNotNull();
        assertThat(sut.get())
                .extracting("instagramUserInfo.id", "instagramUserInfo.accessToken")
                .containsExactly(instagramUserId, token);
    }

    @Test
    @DisplayName("기존 유저가 인스타그램으로 로그인 시 갱신 로직을 수행한 뒤, 유저 정보를 조회한다.")
    void loginByInstagram() throws Exception {
        // given
        String code = "12345";
        String token = "token";
        Long instagramUserId = 123L;
        userJpaRepository.save(User.builder()
                .name("사용자")
                .instagramUserInfo(InstagramUserInfo.builder()
                        .id(instagramUserId)
                        .accessToken(token)
                        .build())
                .build());

        given(instagramRestApiManager.getShortLivedTokenAndUserId(any()))
                .willReturn(new ShortLivedTokenResponse(token, instagramUserId));

        // when
        Long userId = loginService.loginWithInstagram(code);
        Optional<User> sut = userJpaRepository.findById(userId);
        // then
        then(instagramRestApiManager).should().updateInstagramToken(any(),any());
        assertThat(sut).isNotNull();
        assertThat(sut.get())
                .extracting("instagramUserInfo.id", "instagramUserInfo.accessToken")
                .containsExactly(instagramUserId, token);
    }

}