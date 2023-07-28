package swm.s3.coclimb.api.adapter.out.instagram;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponse;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponse;
import swm.s3.coclimb.api.application.port.out.instagram.InstagramAuthPort;
import swm.s3.coclimb.config.ServerClock;
import swm.s3.coclimb.domain.user.Instagram;

import java.util.List;


@Component
@RequiredArgsConstructor
public class InstagramRestApiManager implements InstagramAuthPort {

    private final InstagramRestApi instagramRestApi;
    private final ServerClock serverClock;


    @Override
    public Instagram getNewInstagram(ShortLivedTokenResponse shortLivedTokenResponse) {
        LongLivedTokenResponse longLivedTokenResponse = instagramRestApi.getLongLivedToken(shortLivedTokenResponse.getToken());
        return Instagram.builder()
                .userId(shortLivedTokenResponse.getUserId())
                .accessToken(longLivedTokenResponse.getToken())
                .tokenExpireTime(serverClock.getDateTime().plusSeconds(longLivedTokenResponse.getExpiresIn()))
                .build();
    }

    @Override
    public void updateInstagramToken(Instagram instagram, ShortLivedTokenResponse shortLivedTokenResponse) {
        LongLivedTokenResponse longLivedTokenResponse;
        if (instagram.isExpiredFor(serverClock.getDateTime())) {
            longLivedTokenResponse = instagramRestApi.getLongLivedToken(shortLivedTokenResponse.getToken());
            instagram.updateAccessToken(longLivedTokenResponse.getToken());
        }else if(instagram.isNeedRefreshFor(serverClock.getDateTime())){
            longLivedTokenResponse = instagramRestApi.refreshLongLivedToken(instagram.getAccessToken());
        } else{
            return;
        }
        instagram.updateTokenExpireTime((serverClock.getDateTime().plusSeconds(longLivedTokenResponse.getExpiresIn())));
    }

    @Override
    public ShortLivedTokenResponse getShortLivedTokenAndUserId(String code) {
        return instagramRestApi.getShortLivedTokenAndUserId(code);
    }

    @Override
    public LongLivedTokenResponse getLongLivedToken(String shortLivedToken) {
        return instagramRestApi.getLongLivedToken(shortLivedToken);
    }

    // TODO 수정필요
    public List<InstagramMediaResponseDto> getMyMedias(String accessToken) throws JsonProcessingException {
        return instagramRestApi.getMyMedias(accessToken);
    }

}
