package swm.s3.coclimb.api.adapter.out.instagram.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortLivedTokenResponseDto {
    @JsonProperty("access_token")
    String shortLivedAccessToken;
    @JsonProperty("user_id")
    Long userId;
}
