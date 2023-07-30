package swm.s3.coclimb.api.adapter.in.web.media.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaCreateRequestDto;

@Getter
@NoArgsConstructor
public class MediaCreateRequest {

    // common attributes
    @NotNull
    Long userId;
    @NotNull
    String platform;
    @NotNull
    String mediaUrl;
    @NotNull
    String mediaType;
    String thumbnailUrl;

    // for instagram
    String instagramMediaId;
    String instagramUserId;
    String instagramPermalink;

    @Builder
    public MediaCreateRequest(Long userId, String platform, String mediaUrl, String mediaType, String thumbnailUrl, String instagramMediaId, String instagramUserId, String instagramPermalink) {
        this.userId = userId;
        this.platform = platform;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramMediaId = instagramMediaId;
        this.instagramUserId = instagramUserId;
        this.instagramPermalink = instagramPermalink;
    }

    public MediaCreateRequestDto toServiceDto() {
        return MediaCreateRequestDto.builder()
                .userId(userId)
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .thumbnailUrl(thumbnailUrl)
                .instagramMediaId(instagramMediaId)
                .instagramUserId(instagramUserId)
                .platform(platform)
                .build();
    }
}
