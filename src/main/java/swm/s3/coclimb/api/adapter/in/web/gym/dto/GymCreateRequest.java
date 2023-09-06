package swm.s3.coclimb.api.adapter.in.web.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymCreateRequestDto;
import swm.s3.coclimb.domain.gym.Location;

@Getter
@NoArgsConstructor
public class GymCreateRequest {
    @NotBlank
    private String name;
    private String address;
    private String phone;
    private Location location;
    private String imageUrl;
    private String homepageUrl;
    private String instagramId;
    private String gradingSystem;

    @Builder
    public GymCreateRequest(String name, String address, String phone, Location location, String imageUrl, String homepageUrl, String instagramId, String gradingSystem) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.location = location;
        this.imageUrl = imageUrl;
        this.homepageUrl = homepageUrl;
        this.instagramId = instagramId;
        this.gradingSystem = gradingSystem;
    }

    public GymCreateRequestDto toServiceDto() {
        return GymCreateRequestDto.builder()
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
