package swm.s3.coclimb.api.adapter.out.instagram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InstagramMediaResponseDto {

    @JsonProperty("id")
    String mediaId;
    @JsonProperty("media_type")
    String mediaType;
    @JsonProperty("media_url")
    String mediaUrl;
    @JsonProperty("thumbnail_url")
    String thumbnailUrl;
    @JsonProperty("permalink")
    String permalink;
}
