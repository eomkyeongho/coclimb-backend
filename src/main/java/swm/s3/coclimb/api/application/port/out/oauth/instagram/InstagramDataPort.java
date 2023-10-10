package swm.s3.coclimb.api.application.port.out.oauth.instagram;

import swm.s3.coclimb.api.adapter.out.oauth.instagram.dto.InstagramMediaResponseDto;

import java.util.List;

public interface InstagramDataPort {
    List<InstagramMediaResponseDto> getMyMedias(String accessToken);
}
