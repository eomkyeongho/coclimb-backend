package swm.s3.coclimb.api.adapter.in.web.media.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.media.MediaProblemInfo;

@Getter
public class MediaInfoResponse {
    Long id;
    String username;
    String platform;
    String mediaUrl;
    String thumbnailUrl;
    String description;

    MediaProblemInfo problem;

    @Builder
    private MediaInfoResponse(Long id, String username, String platform, String mediaUrl, String thumbnailUrl, String description, MediaProblemInfo problem) {
        this.id = id;
        this.username = username;
        this.platform = platform;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.problem = problem;
    }

    public static MediaInfoResponse of(Media media) {
        return MediaInfoResponse.builder()
                .id(media.getId())
                .username(media.getUser().getName())
                .platform(media.getPlatform())
                .mediaUrl(media.getMediaUrl())
                .thumbnailUrl(media.getThumbnailUrl())
                .description(media.getDescription())
                .problem(media.getMediaProblemInfo())
                .build();
    }
}
