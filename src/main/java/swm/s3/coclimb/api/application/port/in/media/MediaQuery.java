package swm.s3.coclimb.api.application.port.in.media;

import org.springframework.data.domain.Page;
import swm.s3.coclimb.api.adapter.out.oauth.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaPageRequestDto;
import swm.s3.coclimb.domain.media.Media;

import java.util.List;

public interface MediaQuery {
    List<InstagramMediaResponseDto> getMyInstagramVideos(String accessToken);

    Page<Media> getPagedMedias(MediaPageRequestDto requestDto);

    Page<Media> getPagedMediasByGymName(String gymName, MediaPageRequestDto requestDto);

    Page<Media> getPagedMediasByUserName(String userName, MediaPageRequestDto requestDto);
    Page<Media> getPagedMediasByGymNameAndUserName(String gymName, String userName, MediaPageRequestDto requestDto);

    Media getMediaById(Long mediaId);
}
