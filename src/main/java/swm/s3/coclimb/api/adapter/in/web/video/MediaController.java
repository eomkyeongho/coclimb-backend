package swm.s3.coclimb.api.adapter.in.web.video;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.ApiResponse;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MediaController {

    private final MediaQuery mediaQuery;

    @GetMapping("/medias/my-videos")
    public ApiResponse<List<InstagramMediaResponseDto>> getMyVideos(HttpSession httpSession) throws JsonProcessingException {
        List<InstagramMediaResponseDto> myVideos = mediaQuery.getMyVideos((String) httpSession.getAttribute("instagramAccessToken"));
        return ApiResponse.ok(myVideos);
    }
}
