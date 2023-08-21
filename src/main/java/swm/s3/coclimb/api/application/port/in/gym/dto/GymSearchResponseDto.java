package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.gym.Gym;

@Getter
public class GymSearchResponseDto {
    String name;

    @Builder
    public GymSearchResponseDto(String name) {
        this.name = name;
    }
    public static GymSearchResponseDto of(Gym gym) {
        return GymSearchResponseDto.builder()
                .name(gym.getName())
                .build();
    }
}
