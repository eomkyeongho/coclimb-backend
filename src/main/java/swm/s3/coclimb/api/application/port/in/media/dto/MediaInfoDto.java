package swm.s3.coclimb.api.application.port.in.media.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.Media;

@Getter
public class MediaInfoDto {
    String platform;
    String mediaType;
    String mediaUrl;
    String thumbnailUrl;
    String instagramPermalink;

    @Builder
    public MediaInfoDto(String platform, String mediaType, String mediaUrl, String thumbnailUrl, String instagramPermalink) {
        this.platform = platform;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramPermalink = instagramPermalink;
    }

    public static MediaInfoDto of(Media media){
        return MediaInfoDto.builder()
                .platform(media.getPlatform())
                .mediaType(media.getMediaType())
                .mediaUrl(media.getMediaUrl())
                .thumbnailUrl(media.getThumbnailUrl())
                .instagramPermalink(media.getInstagramPermalink())
                .build();
    }
}
