package swm.s3.coclimb.api.application.port.in.gym;

import swm.s3.coclimb.api.application.port.in.gym.dto.GymInfoResponseDto;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymLocationResponseDto;

import java.util.List;

public interface GymQuery {
    GymInfoResponseDto getGymInfoByName(String name);

    List<GymLocationResponseDto> getGymLocations();
}
