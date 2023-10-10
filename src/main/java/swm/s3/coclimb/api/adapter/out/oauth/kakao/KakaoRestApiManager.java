package swm.s3.coclimb.api.adapter.out.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import swm.s3.coclimb.api.adapter.out.oauth.kakao.dto.KakaoTokenResponse;
import swm.s3.coclimb.api.application.port.out.oauth.kakao.KakaoAuthPort;
import swm.s3.coclimb.api.application.port.out.oauth.kakao.KakaoDataPort;

@Component
@RequiredArgsConstructor
public class KakaoRestApiManager implements KakaoAuthPort, KakaoDataPort {
    private final KakaoRestApi kakaoRestApi;

    @Override
    public KakaoTokenResponse getToken(String code) {
        return kakaoRestApi.getToken(code);
    }

    @Override
    public Long getKakaoUserId(String accessToken) {
        return kakaoRestApi.getUserId(accessToken);
    }
}
