package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import swm.s3.coclimb.domain.gym.Gym;

import java.util.List;

@Getter
public class GymPageResponse {
    private List<Gym> gyms;
    private int page;
    private int size;
    private int totalPage;

    @Builder
    private GymPageResponse(List<Gym> gyms, int page, int size, int totalPage) {
        this.gyms = gyms;
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    public static GymPageResponse of(Page<Gym> pagedGyms) {
        return GymPageResponse.builder()
                .gyms(pagedGyms.getContent())
                .page(pagedGyms.getNumber())
                .size(pagedGyms.getSize())
                .totalPage(pagedGyms.getTotalPages())
                .build();
    }
}
