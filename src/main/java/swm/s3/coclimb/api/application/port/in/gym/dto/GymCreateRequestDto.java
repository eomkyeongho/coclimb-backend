package swm.s3.coclimb.api.application.port.in.gym.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.gym.Gym;
import swm.s3.coclimb.domain.gym.Location;

@Getter
@Builder
public class GymCreateRequestDto {
    private String name;
    private String address;
    private String phone;
    private Location location;
    private String imageUrl;
    private String homepageUrl;
    private String instagramId;
    private String gradingSystem;

    public Gym toEntity() {
        return Gym.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .location(location)
                .imageUrl(imageUrl)
                .homepageUrl(homepageUrl)
                .instagramId(instagramId)
                .gradingSystem(gradingSystem)
                .build();
    }
}
