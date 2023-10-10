package swm.s3.coclimb.api.application.port.out.oauth.kakao;

public interface KakaoDataPort {

    Long getKakaoUserId(String accessToken);
}
