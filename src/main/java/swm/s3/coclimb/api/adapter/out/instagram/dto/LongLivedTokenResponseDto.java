package swm.s3.coclimb.api.adapter.out.instagram.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LongLivedTokenResponseDto {
    String longLivedAccessToken;
    String tokenType;
    Long expiresIn;
}
