package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.domain.gym.Location;
import swm.s3.coclimb.domain.gymlike.GymLike;

@Getter
@NoArgsConstructor
public class GymLikesResponseDto {
    String name;
    String address;
    String phone;
    String instagramId;
    String imageUrl;
    Location location;

    @Builder
    public GymLikesResponseDto(String name, String address, String phone, String instagramId, String imageUrl, Location location) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.instagramId = instagramId;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public static GymLikesResponseDto of(GymLike gymLike) {
        return GymLikesResponseDto.builder()
                .name(gymLike.getGym().getName())
                .address(gymLike.getGym().getAddress())
                .phone(gymLike.getGym().getPhone())
                .instagramId(gymLike.getGym().getInstagramId())
                .imageUrl(gymLike.getGym().getImageUrl())
                .location(gymLike.getGym().getLocation())
                .build();
    }
}
