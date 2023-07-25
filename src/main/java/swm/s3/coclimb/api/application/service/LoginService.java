package swm.s3.coclimb.api.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramRestApiManager;
import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponseDto;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponseDto;
import swm.s3.coclimb.api.application.port.in.login.LoginCommand;
import swm.s3.coclimb.api.application.port.in.login.dto.SessionDataDto;
import swm.s3.coclimb.api.application.port.out.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.user.UserUpdatePort;
import swm.s3.coclimb.domain.User;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements LoginCommand {

    private final UserLoadPort userLoadPort;
    private final UserUpdatePort userUpdatePort;

    private final InstagramRestApiManager instagramRestApiManager;

    @Override
    @Transactional
    public SessionDataDto authenticateWithInstagram(String code) throws JsonProcessingException {
        ShortLivedTokenResponseDto shortLivedTokenResponseDto = instagramRestApiManager.getShortLivedAccessTokenAndUserId(code);
        User user = userLoadPort.findByInstagramUserId(shortLivedTokenResponseDto.getUserId()).orElse(null);

        if(user == null) {
            user = createUser(shortLivedTokenResponseDto);
        } else {
            updateAccessToken(user, shortLivedTokenResponseDto);
        }

        return SessionDataDto.builder()
                .instagramUserId(user.getInstagramUserId())
                .instagramAccessToken(user.getInstagramAccessToken())
                .build();
    }

    private User createUser(ShortLivedTokenResponseDto shortLivedTokenResponseDto) throws JsonProcessingException {
        LongLivedTokenResponseDto longLivedTokenResponseDto = instagramRestApiManager.getLongLivedAccessToken(shortLivedTokenResponseDto.getShortLivedAccessToken());

        User savedUser = User.builder()
                .instagramUserId(shortLivedTokenResponseDto.getUserId())
                .instagramAccessToken(longLivedTokenResponseDto.getLongLivedAccessToken())
                .instagramTokenExpireDate(LocalDate.now().plusDays(longLivedTokenResponseDto.getExpiresIn()/86400))
                .build();

        userUpdatePort.save(savedUser);

        return savedUser;
    }

    private void updateAccessToken(User user, ShortLivedTokenResponseDto shortLivedTokenResponseDto) throws JsonProcessingException {
        Period gap = Period.between(LocalDate.now(), user.getInstagramTokenExpireDate());

        if(gap.getMonths() <= 0) {
            LongLivedTokenResponseDto longLivedTokenResponseDto;

            if(gap.getDays() > 0) {
                longLivedTokenResponseDto = instagramRestApiManager.refreshLongLivedToken(user.getInstagramAccessToken());
            } else {
                longLivedTokenResponseDto = instagramRestApiManager.getLongLivedAccessToken(shortLivedTokenResponseDto.getShortLivedAccessToken());
            }

            user.update(User.builder()
                    .instagramAccessToken(longLivedTokenResponseDto.getLongLivedAccessToken())
                    .instagramTokenExpireDate(LocalDate.now().plusDays(longLivedTokenResponseDto.getExpiresIn()/86400))
                    .build());
        }
    }
}
