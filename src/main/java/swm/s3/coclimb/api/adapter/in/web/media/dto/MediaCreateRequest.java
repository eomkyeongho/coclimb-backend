package swm.s3.coclimb.api.adapter.in.web.media.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaCreateRequestDto;
import swm.s3.coclimb.domain.user.User;

@Getter
@NoArgsConstructor
public class MediaCreateRequest {

    // common attributes
    @NotNull
    String platform;
    @NotNull
    String mediaUrl;
    @NotNull
    String mediaType;
    String thumbnailUrl;

    // instagram info
    String instagramMediaId;
    String instagramPermalink;

    // problem info
    String gymName;
    String perceivedDifficulty;
    String problemColor;
    String problemType;
    Boolean isClear;

    @Builder
    public MediaCreateRequest(String platform, String mediaUrl, String mediaType, String thumbnailUrl, String instagramMediaId, String instagramPermalink, String gymName, String perceivedDifficulty, String problemColor, String problemType, Boolean isClear) {
        this.platform = platform;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramMediaId = instagramMediaId;
        this.instagramPermalink = instagramPermalink;
        this.gymName = gymName;
        this.perceivedDifficulty = perceivedDifficulty;
        this.problemColor = problemColor;
        this.problemType = problemType;
        this.isClear = isClear;
    }

    public MediaCreateRequestDto toServiceDto(User user) {
        return MediaCreateRequestDto.builder()
                .userId(user.getId())
                .username(user.getName())
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .thumbnailUrl(thumbnailUrl)
                .platform(platform)
                .instagramMediaId(instagramMediaId)
                .instagramUserId(user.getInstagramUserInfo().getId())
                .instagramPermalink(instagramPermalink)
                .gymName(gymName)
                .perceivedDifficulty(perceivedDifficulty)
                .problemColor(problemColor)
                .problemType(problemType)
                .isClear(isClear)
                .build();
    }
}
