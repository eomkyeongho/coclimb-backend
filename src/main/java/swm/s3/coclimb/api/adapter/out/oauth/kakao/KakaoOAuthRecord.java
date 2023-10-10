package swm.s3.coclimb.api.adapter.out.oauth.kakao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoOAuthRecord {
    private String clientId;
    private String redirectUri;

    public KakaoOAuthRecord(@Value("${secret.kakao.client_id}") String clientId,
                            @Value("${secret.kakao.redirect_uri}") String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }
}
