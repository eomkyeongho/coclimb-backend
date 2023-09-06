package swm.s3.coclimb.api.application.port.in.media.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.user.User;

@Getter
@Builder
public class MediaDeleteRequestDto {
    Long mediaId;
    User user;
}
