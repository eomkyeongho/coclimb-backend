package swm.s3.coclimb.api.adapter.in.web.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swm.s3.coclimb.api.adapter.in.web.media.dto.InstagramMyVideosResponse;
import swm.s3.coclimb.api.adapter.in.web.media.dto.MediaCreateRequest;
import swm.s3.coclimb.api.adapter.in.web.media.dto.MediaInfosResponse;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.application.port.in.media.MediaCommand;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaInfoDto;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class MediaController {

    private final MediaQuery mediaQuery;
    private final MediaCommand mediaCommand;

    @PostMapping("/medias")
    public ResponseEntity<Void> createMedia(@RequestBody @Valid MediaCreateRequest mediaCreateRequest) {
        mediaCommand.createMedia(mediaCreateRequest.toServiceDto());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @GetMapping("/medias/instagram/my-videos")
    public ResponseEntity<InstagramMyVideosResponse> getMyInstagramVideos(HttpSession httpSession) throws JsonProcessingException {
        List<InstagramMediaResponseDto> myInstagramVideos = mediaQuery.getMyInstagramVideos((String) httpSession.getAttribute("instagramAccessToken"));
        return ResponseEntity.ok(InstagramMyVideosResponse.of(myInstagramVideos));
    }

    @GetMapping("/medias")
    public ResponseEntity<MediaInfosResponse> getAllMedias(@RequestParam(required = false) String mediaType) {
        List<MediaInfoDto> mediaInfos;

        if(mediaType == null) {
            mediaInfos = mediaQuery.findAll();
        } else if(mediaType.equals("VIDEO")) {
            mediaInfos = mediaQuery.findAllVideos();
        } else {
            mediaInfos = mediaQuery.findAll();
        }

        return ResponseEntity.ok(MediaInfosResponse.of(mediaInfos));
    }
}
