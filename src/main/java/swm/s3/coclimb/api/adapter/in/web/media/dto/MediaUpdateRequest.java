package swm.s3.coclimb.api.adapter.in.web.media.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaUpdateRequestDto;
import swm.s3.coclimb.domain.user.User;

@Getter
@NoArgsConstructor
public class MediaUpdateRequest {
    String description;

    @Builder
    public MediaUpdateRequest(String description) {
        this.description = description;
    }

    public MediaUpdateRequestDto toServiceDto(Long mediaId, User user) {
        return MediaUpdateRequestDto.builder()
                .mediaId(mediaId)
                .user(user)
                .description(description)
                .build();
    }
}
