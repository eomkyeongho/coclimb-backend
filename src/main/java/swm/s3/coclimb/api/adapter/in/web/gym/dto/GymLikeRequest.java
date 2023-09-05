package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymLikeRequestDto;
import swm.s3.coclimb.domain.user.User;

@Getter
@NoArgsConstructor
public class GymLikeRequest {
    @NotBlank
    String name;


    @Builder
    public GymLikeRequest(String name) {
        this.name = name;
    }

    public GymLikeRequestDto toServiceDto(User user) {
        return GymLikeRequestDto.builder()
                .gymName(name)
                .user(user)
                .build();
    }
}
