package swm.s3.coclimb.api.adapter.out.instagram.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShortLivedTokenResponseDto {
    String shortLivedAccessToken;
    Long userId;
}
