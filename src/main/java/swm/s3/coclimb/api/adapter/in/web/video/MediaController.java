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

    @GetMapping("/medias/instagram/my-videos")
    public ApiResponse<List<InstagramMediaResponseDto>> getMyInstagramVideos(HttpSession httpSession) throws JsonProcessingException {
        List<InstagramMediaResponseDto> myInstagramVideos = mediaQuery.getMyInstagramVideos((String) httpSession.getAttribute("instagramAccessToken"));
        return ApiResponse.ok(myInstagramVideos);
    }
}
