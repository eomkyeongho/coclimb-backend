package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymSearchResponseDto;

import java.util.List;

@Getter
public class GymSearchResponse {
    List<GymSearchResponseDto> gyms;
    private int count;

    @Builder
    public GymSearchResponse(List<GymSearchResponseDto> gyms, int count) {
        this.gyms = gyms;
        this.count = count;
    }

    public static GymSearchResponse of(List<GymSearchResponseDto> gyms) {
        return GymSearchResponse.builder()
                .gyms(gyms)
                .count(gyms.size())
                .build();
    }
}
