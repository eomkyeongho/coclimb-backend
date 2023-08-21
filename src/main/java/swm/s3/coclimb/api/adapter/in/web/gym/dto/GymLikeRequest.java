package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymLikeRequestDto;
import swm.s3.coclimb.domain.user.User;

@Getter
@NoArgsConstructor
public class GymLikeRequest {
    @NotNull
    Long gymId;

    @Builder
    public GymLikeRequest(Long gymId) {
        this.gymId = gymId;
    }

    public GymLikeRequestDto toServiceDto(User user) {
        return GymLikeRequestDto.builder()
                .gymId(gymId)
                .user(user)
                .build();
    }
}
