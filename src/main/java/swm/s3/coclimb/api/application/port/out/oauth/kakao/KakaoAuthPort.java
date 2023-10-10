package swm.s3.coclimb.api.application.port.out.oauth.kakao;

import swm.s3.coclimb.api.adapter.out.oauth.kakao.dto.KakaoTokenResponse;

public interface KakaoAuthPort {
    KakaoTokenResponse getToken(String code);
}
