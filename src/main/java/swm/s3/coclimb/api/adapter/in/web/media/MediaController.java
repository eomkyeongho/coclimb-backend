package swm.s3.coclimb.api.adapter.in.web.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.ApiResponse;
import swm.s3.coclimb.api.adapter.in.web.media.dto.InstagramMyVideosResponse;
import swm.s3.coclimb.api.adapter.in.web.media.dto.MediaInfosResponse;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaInfoDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MediaController {

    private final MediaQuery mediaQuery;

    @GetMapping("/medias/instagram/my-videos")
    public ApiResponse<InstagramMyVideosResponse> getMyInstagramVideos(HttpSession httpSession) throws JsonProcessingException {
        List<InstagramMediaResponseDto> myInstagramVideos = mediaQuery.getMyInstagramVideos((String) httpSession.getAttribute("instagramAccessToken"));
        return ApiResponse.ok(InstagramMyVideosResponse.of(myInstagramVideos));
    }

    @GetMapping("/medias")
    public ApiResponse<MediaInfosResponse> getAllMedias(@RequestParam(required = false) String mediaType) {
        List<MediaInfoDto> mediaInfos;

        if(mediaType == null) {
            mediaInfos = mediaQuery.findAll();
        } else if(mediaType.equals("VIDEO")) {
            mediaInfos = mediaQuery.findAllVideos();
        } else {
            throw new IllegalArgumentException("mediaType must be null or VIDEO");
        }

        return ApiResponse.ok(MediaInfosResponse.of(mediaInfos));
    }
}
