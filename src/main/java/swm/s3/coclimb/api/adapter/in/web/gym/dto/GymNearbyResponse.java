package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymNearbyResponseDto;

import java.util.List;

@Getter
public class GymNearbyResponse {
    private List<GymNearbyResponseDto> gyms;
    private int count;

    @Builder
    public GymNearbyResponse(List<GymNearbyResponseDto> gyms, int count) {
        this.gyms = gyms;
        this.count = count;
    }

    public static GymNearbyResponse of(List<GymNearbyResponseDto> gyms) {
        return GymNearbyResponse.builder()
                .gyms(gyms)
                .count(gyms.size())
                .build();
    }
}
