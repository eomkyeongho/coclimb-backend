package swm.s3.coclimb.api.application.port.in.media.dto;

import lombok.Builder;
import swm.s3.coclimb.domain.media.InstagramMediaInfo;
import swm.s3.coclimb.domain.media.Media;

public class MediaCreateRequestDto {
    Long userId;
    String platform;
    String mediaUrl;
    String mediaType;
    String thumbnailUrl;

    String instagramMediaId;
    String instagramUserId;
    String instagramPermalink;

    @Builder
    public MediaCreateRequestDto(Long userId, String mediaUrl, String mediaType, String thumbnailUrl, String instagramMediaId, String instagramUserId, String platform) {
        this.userId = userId;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.thumbnailUrl = thumbnailUrl;
        this.platform = platform;
    }

    public Media toEntity() {
        return Media.builder()
                .platform(platform)
                .userId(userId)
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .thumbnailUrl(thumbnailUrl)
                .instagramMediaInfo(InstagramMediaInfo.builder()
                        .mediaId(instagramMediaId)
                        .userId(instagramUserId)
                        .permalink(instagramPermalink)
                        .build())
                .build();
    }
}
