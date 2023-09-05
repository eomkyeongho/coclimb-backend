package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.adapter.out.persistence.gym.dto.GymNearby;

@Getter
@NoArgsConstructor
public class GymNearbyResponseDto {
    private String name;
    private float distance;
    private String address;
    private String imageUrl;
    private String instagramId;
    private String phone;

    @Builder
    public GymNearbyResponseDto(String name, String instagramId, float distance, String address, String imageUrl, String phone) {
        this.name = name;
        this.instagramId = instagramId;
        this.distance = distance;
        this.address = address;
        this.imageUrl = imageUrl;
        this.phone = phone;
    }

    public static GymNearbyResponseDto of(GymNearby gymNearby) {
        return GymNearbyResponseDto.builder()
                .name(gymNearby.getName())
                .instagramId(gymNearby.getInstagramId())
                .distance(gymNearby.getDistance())
                .address(gymNearby.getAddress())
                .imageUrl(gymNearby.getImageUrl())
                .phone(gymNearby.getPhone())
                .build();
    }
}
