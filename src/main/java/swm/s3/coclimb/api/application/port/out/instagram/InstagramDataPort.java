package swm.s3.coclimb.api.application.port.out.instagram;

import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;

import java.util.List;

public interface InstagramDataPort {
    List<InstagramMediaResponseDto> getMyMedias(String accessToken);
}
