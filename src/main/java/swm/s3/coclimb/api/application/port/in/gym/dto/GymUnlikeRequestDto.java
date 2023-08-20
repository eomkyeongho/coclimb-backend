package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GymUnlikeRequestDto {
    Long gymId;
    Long userId;
}
