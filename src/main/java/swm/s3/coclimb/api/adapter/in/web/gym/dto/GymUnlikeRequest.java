package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymUnlikeRequestDto;

@Getter
@NoArgsConstructor
public class GymUnlikeRequest {
    @NotNull
    Long gymId;

    @Builder
    public GymUnlikeRequest(Long gymId) {
        this.gymId = gymId;
    }

    public GymUnlikeRequestDto toServiceDto(Long userId) {
        return GymUnlikeRequestDto.builder()
                .userId(userId)
                .gymId(gymId)
                .build();
    }
}
