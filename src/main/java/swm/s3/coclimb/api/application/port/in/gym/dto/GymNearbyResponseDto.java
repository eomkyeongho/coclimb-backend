package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.adapter.out.persistence.gym.dto.GymNearby;
import swm.s3.coclimb.domain.gym.Location;

@Getter
@NoArgsConstructor
public class GymNearbyResponseDto {
    private String name;
    private Location location;
    private float distance;

    @Builder
    public GymNearbyResponseDto(String name, Location location, float distance) {
        this.name = name;
        this.location = location;
        this.distance = distance;
    }

    public static GymNearbyResponseDto of(GymNearby gymNearby) {
        return GymNearbyResponseDto.builder()
                .name(gymNearby.getName())
                .location(Location.of(gymNearby.getLatitude(), gymNearby.getLongitude()))
                .distance(gymNearby.getDistance())
                .build();
    }
}
