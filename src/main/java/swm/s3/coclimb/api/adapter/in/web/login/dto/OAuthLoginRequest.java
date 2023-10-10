package swm.s3.coclimb.api.adapter.in.web.login.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthLoginRequest {
    @NotNull
    String code;

    private OAuthLoginRequest(String code) {
        this.code = code;
    }

    public static OAuthLoginRequest of(String code) {
        return new OAuthLoginRequest(code);
    }
}
