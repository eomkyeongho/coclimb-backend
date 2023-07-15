package swm.s3.coclimb.api.adapter.in.web.gym;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.api.application.port.in.gym.GymUpdateRequestDto;
import swm.s3.coclimb.domain.Gym;
import swm.s3.coclimb.domain.Location;

@Getter
@NoArgsConstructor
public class GymUpdateRequest {
    @NotBlank(message = "수정할 암장의 이름은 필수입니다.")
    private String targetName;
    private String name;
    private String address;
    private String phone;
    private Location location;

    @Builder
    public GymUpdateRequest(String targetName, String name, String address, String phone, Location location) {
        this.targetName = targetName;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.location = location;
    }

    public GymUpdateRequestDto toServiceDto() {
        return GymUpdateRequestDto.builder()
                .targetName(targetName)
                .updateInfo(Gym.builder()
                        .name(name)
                        .address(address)
                        .phone(phone)
                        .location(location)
                        .build())
                .build();

    }
}
