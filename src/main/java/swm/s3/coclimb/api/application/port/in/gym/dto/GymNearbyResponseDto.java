package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.adapter.out.persistence.gym.dto.GymNearby;
import swm.s3.coclimb.domain.gym.Location;

@Getter
@NoArgsConstructor
public class GymNearbyResponseDto {
    private Long id;
    private String name;
    private Location location;
    private float distance;
    private String address;

    @Builder
    public GymNearbyResponseDto(Long id, String name, Location location, float distance, String address) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.address = address;
    }

    public static GymNearbyResponseDto of(GymNearby gymNearby) {
        return GymNearbyResponseDto.builder()
                .id(gymNearby.getId())
                .name(gymNearby.getName())
                .location(Location.of(gymNearby.getLatitude(), gymNearby.getLongitude()))
                .distance(gymNearby.getDistance())
                .address(gymNearby.getAddress())
                .build();
    }
}
