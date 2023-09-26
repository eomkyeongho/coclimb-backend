package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymLikesResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
public class GymLikesResponse {
    private List<?> gyms;
    private int count;

    @Builder
    public GymLikesResponse(List<GymLikesResponseDto> gyms, int count, int resultContent) {
        switch(resultContent) {
            case 0:
                this.gyms = gyms;
                break;
            case 1:
                List<String> gymNames = new ArrayList<>();
                IntStream.range(0, gyms.size()).forEach(i -> gymNames.add(gyms.get(i).getName()));
                this.gyms = gymNames;
        }
        this.count = count;
    }

    public static GymLikesResponse of(List<GymLikesResponseDto> gyms, int resultContent) {
        return GymLikesResponse.builder()
                .gyms(gyms)
                .count(gyms.size())
                .resultContent(resultContent)
                .build();
    }
}
