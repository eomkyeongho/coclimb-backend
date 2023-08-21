package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.user.User;

@Builder
@Getter
public class GymLikeRequestDto {
    User user;
    Long gymId;
}
