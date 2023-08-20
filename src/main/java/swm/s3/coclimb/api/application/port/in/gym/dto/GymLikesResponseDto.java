package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.domain.gymlike.GymLike;

@Getter
@NoArgsConstructor
public class GymLikesResponseDto {
    Long id;
    String name;

    @Builder
    public GymLikesResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GymLikesResponseDto of(GymLike gymLike) {
        return GymLikesResponseDto.builder()
                .id(gymLike.getGym().getId())
                .name(gymLike.getGym().getName())
                .build();
    }
}
