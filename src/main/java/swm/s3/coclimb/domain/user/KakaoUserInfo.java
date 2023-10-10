package swm.s3.coclimb.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserInfo {
    @Column(name = "kakao_user_id")
    private Long id;
    @Column(name = "kakao_token_type")
    private String tokenType;
    @Column(name = "kakao_access_token")
    private String accessToken;
    @Column(name = "kakao_token_expire_time")
    private Long expiresIn;
    @Column(name = "kakao_refresh_token")
    private String refreshToken;
    @Column(name = "kakao_refresh_token_expire_time")
    private Long refreshTokenExpiresIn;
    @Column(name = "kakao_scope")
    private String scope;

    @Builder
    public KakaoUserInfo(Long id, String tokenType, String accessToken, Long expiresIn, String refreshToken, Long refreshTokenExpiresIn, String scope) {
        this.id = id;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.scope = scope;
    }
}
