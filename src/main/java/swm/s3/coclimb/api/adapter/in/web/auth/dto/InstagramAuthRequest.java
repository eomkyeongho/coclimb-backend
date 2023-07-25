package swm.s3.coclimb.api.adapter.in.web.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InstagramAuthRequest {
    @NotNull
    String code;
}
