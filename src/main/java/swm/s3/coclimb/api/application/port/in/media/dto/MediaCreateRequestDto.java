package swm.s3.coclimb.api.application.port.in.media.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.media.InstagramMediaInfo;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.media.ProblemInfo;

@Getter
public class MediaCreateRequestDto {
    Long userId;
    String username;
    String platform;
    String mediaUrl;
    String mediaType;
    String thumbnailUrl;

    String instagramMediaId;
    Long instagramUserId;
    String instagramPermalink;

    String gymName;
    String perceivedDifficulty;
    String problemColor;
    String problemType;
    Boolean isClear;

    @Builder
    public MediaCreateRequestDto(Long userId, String username, String platform, String mediaUrl, String mediaType, String thumbnailUrl, String instagramMediaId, Long instagramUserId, String instagramPermalink, String gymName, String perceivedDifficulty, String problemColor, String problemType, Boolean isClear) {
        this.userId = userId;
        this.username = username;
        this.platform = platform;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramMediaId = instagramMediaId;
        this.instagramUserId = instagramUserId;
        this.instagramPermalink = instagramPermalink;
        this.gymName = gymName;
        this.perceivedDifficulty = perceivedDifficulty;
        this.problemColor = problemColor;
        this.problemType = problemType;
        this.isClear = isClear;
    }

    public Media toEntity() {
        return Media.builder()
                .platform(platform)
                .userId(userId)
                .username(username)
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .thumbnailUrl(thumbnailUrl)
                .instagramMediaInfo(InstagramMediaInfo.builder()
                        .id(instagramMediaId)
                        .userId(instagramUserId)
                        .permalink(instagramPermalink)
                        .build())
                .problemInfo(ProblemInfo.builder()
                        .gymName(gymName)
                        .perceivedDifficulty(perceivedDifficulty)
                        .color(problemColor)
                        .type(problemType)
                        .isClear(isClear)
                        .build())
                .build();
    }
}
