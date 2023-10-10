package swm.s3.coclimb.api.adapter.in.web.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthLoginPageResponse {
    String loginPageUrl;

    @Builder
    public OAuthLoginPageResponse(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
    }
}
