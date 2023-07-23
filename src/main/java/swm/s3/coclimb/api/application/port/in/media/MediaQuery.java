package swm.s3.coclimb.api.application.port.in.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;

import java.util.List;

public interface MediaQuery {
    List<InstagramMediaResponseDto> getMyInstagramVideos(String accessToken) throws JsonProcessingException;
}
