package swm.s3.coclimb.api.application.port.in.media.dto;

import lombok.Builder;
import swm.s3.coclimb.domain.Media;

public class MediaCreateRequestDto {
    Long userId;
    String platform;
    String mediaUrl;
    String mediaType;
    String thumbnailUrl;

    String instagramMediaId;
    String instagramUserId;

    @Builder
    public MediaCreateRequestDto(Long userId, String mediaUrl, String mediaType, String thumbnailUrl, String instagramMediaId, String instagramUserId, String platform) {
        this.userId = userId;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramMediaId = instagramMediaId;
        this.instagramUserId = instagramUserId;
        this.platform = platform;
    }

    public Media toEntity() {
        return Media.builder()
                .platform(platform)
                .userId(userId)
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .thumbnailUrl(thumbnailUrl)
                .instagramMediaId(instagramMediaId)
                .instagramUserId(instagramUserId)
                .build();
    }
}
