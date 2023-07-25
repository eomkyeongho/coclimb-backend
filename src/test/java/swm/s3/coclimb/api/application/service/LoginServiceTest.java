package swm.s3.coclimb.api.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramRestApiManager;
import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponseDto;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponseDto;
import swm.s3.coclimb.api.adapter.out.persistence.user.UserJpaRepository;
import swm.s3.coclimb.api.application.port.out.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.user.UserUpdatePort;
import swm.s3.coclimb.domain.User;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Transactional
class LoginServiceTest extends IntegrationTestSupport {
    LoginService loginService;
    @Mock
    InstagramRestApiManager instagramRestApiManager;
    @Autowired
    UserLoadPort userLoadPort;
    @Autowired
    UserUpdatePort userUpdatePort;
    @Autowired
    UserJpaRepository userJpaRepository;

    @BeforeEach
    void setUp() {
        loginService = new LoginService(userLoadPort, userUpdatePort, instagramRestApiManager);
    }

    @AfterEach
    void tearDown() {
        userJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("첫 로그인 시 유저 생성 후 저장한다.")
    void firstLogin() throws JsonProcessingException {
        // given
        Long instagramUserId = 1L;
        when(instagramRestApiManager.getShortLivedAccessTokenAndUserId(any(String.class))).thenReturn(new ShortLivedTokenResponseDto(
                "shortLivedAccessToken",
                instagramUserId));
        when(instagramRestApiManager.getLongLivedAccessToken(any(String.class))).thenReturn(new LongLivedTokenResponseDto(
                "longLivedAccessToken",
                "Bearer",
                5184000L));

        // when
        loginService.authenticateWithInstagram("test");
        User sut = userLoadPort.findByInstagramUserId(instagramUserId).orElse(null);

        // then
        verify(instagramRestApiManager, times(1)).getShortLivedAccessTokenAndUserId(any(String.class));
        verify(instagramRestApiManager, times(1)).getLongLivedAccessToken(any(String.class));
        assertThat(sut.getInstagramAccessToken()).isEqualTo("longLivedAccessToken");
    }

    @Test
    @DisplayName("이미 유저가 존재하고 토큰 유효 기간이 31일 이상이면 토큰을 갱신하지 않는다.")
    void notUpdateToken() throws JsonProcessingException {
        // given
        LocalDate now = LocalDate.now();
        userJpaRepository.save(User.builder()
                .instagramUserId(1L)
                .instagramAccessToken("longLivedAccessToken")
                .instagramTokenExpireDate(now.plusDays(31))
                .build());

        Long instagramUserId = 1L;
        when(instagramRestApiManager.getShortLivedAccessTokenAndUserId(any(String.class))).thenReturn(new ShortLivedTokenResponseDto(
                "shortLivedAccessToken",
                instagramUserId));
        lenient().when(instagramRestApiManager.getLongLivedAccessToken(any(String.class))).thenReturn(new LongLivedTokenResponseDto(
                "newLongLivedAccessToken",
                "Bearer",
                5184000L));

        // when
        loginService.authenticateWithInstagram("test");
        User sut = userLoadPort.findByInstagramUserId(instagramUserId).orElse(null);

        // then
        verify(instagramRestApiManager, times(1)).getShortLivedAccessTokenAndUserId(any(String.class));
        verify(instagramRestApiManager, times(0)).getLongLivedAccessToken(any(String.class));
        verify(instagramRestApiManager, times(0)).refreshLongLivedToken(any(String.class));
        assertThat(sut.getInstagramAccessToken()).isEqualTo("longLivedAccessToken");
    }

    @Test
    @DisplayName("이미 유저가 존재하고 토큰 유효 기간이 31일 미만이면 토큰을 갱신한다.")
    void updateToken() throws JsonProcessingException {
        // given
        LocalDate now = LocalDate.now();
        Long instagramUserId = 1L;

        userJpaRepository.save(User.builder()
                .instagramUserId(instagramUserId)
                .instagramAccessToken("longLivedAccessToken")
                .instagramTokenExpireDate(now.plusDays(30))
                .build());

        when(instagramRestApiManager.getShortLivedAccessTokenAndUserId(any(String.class))).thenReturn(new ShortLivedTokenResponseDto(
                "shortLivedAccessToken",
                instagramUserId));
        when(instagramRestApiManager.refreshLongLivedToken(any(String.class))).thenReturn(new LongLivedTokenResponseDto(
                "refreshedAccessToken",
                "Bearer",
                5184000L));

        // when
        loginService.authenticateWithInstagram("test");
        User sut = userLoadPort.findByInstagramUserId(instagramUserId).orElse(null);

        // then
        verify(instagramRestApiManager, times(1)).getShortLivedAccessTokenAndUserId(any(String.class));
        verify(instagramRestApiManager, times(0)).getLongLivedAccessToken(any(String.class));
        verify(instagramRestApiManager, times(1)).refreshLongLivedToken(any(String.class));
        assertThat(sut.getInstagramAccessToken()).isEqualTo("refreshedAccessToken");
    }

    @Test
    @DisplayName("이미 유저가 존재하고 토큰 유효 기간이 끝났으면 토큰을 새로 발급한다.")
    void reissueToken() throws JsonProcessingException {
        // given
        LocalDate now = LocalDate.now();
        Long instagramUserId = 1L;

        userJpaRepository.save(User.builder()
                .instagramUserId(instagramUserId)
                .instagramAccessToken("expiredAccessToken")
                .instagramTokenExpireDate(now.minusDays(1))
                .build());

        when(instagramRestApiManager.getShortLivedAccessTokenAndUserId(any(String.class))).thenReturn(new ShortLivedTokenResponseDto(
                "shortLivedAccessToken",
                instagramUserId));
        when(instagramRestApiManager.getLongLivedAccessToken(any(String.class))).thenReturn(new LongLivedTokenResponseDto(
                "newLongLivedAccessToken",
                "Bearer",
                5184000L));

        // when
        loginService.authenticateWithInstagram("test");
        User sut = userLoadPort.findByInstagramUserId(instagramUserId).orElse(null);

        // then
        verify(instagramRestApiManager, times(1)).getShortLivedAccessTokenAndUserId(any(String.class));
        verify(instagramRestApiManager, times(1)).getLongLivedAccessToken(any(String.class));
        verify(instagramRestApiManager, times(0)).refreshLongLivedToken(any(String.class));
        assertThat(sut.getInstagramAccessToken()).isEqualTo("newLongLivedAccessToken");
    }
}