package swm.s3.coclimb.api.adapter.in.web.login.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InstagramAuthRequest {
    @NotNull
    String code;
}
