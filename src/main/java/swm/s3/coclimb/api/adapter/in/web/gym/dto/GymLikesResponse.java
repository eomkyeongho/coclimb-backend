package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymLikesResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class GymLikesResponse {
    private List<GymLikesResponseDto> gyms;
    private int count;

    @Builder
    public GymLikesResponse(List<GymLikesResponseDto> gyms, int count) {
        this.gyms = gyms;
        this.count = count;
    }

    public static GymLikesResponse of(List<GymLikesResponseDto> gyms) {
        return GymLikesResponse.builder()
                .gyms(gyms)
                .count(gyms.size())
                .build();
    }
}
