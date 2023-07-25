package swm.s3.coclimb.api.application.port.in.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionDataDto {
    Long instagramUserId;
    String instagramAccessToken;
}
