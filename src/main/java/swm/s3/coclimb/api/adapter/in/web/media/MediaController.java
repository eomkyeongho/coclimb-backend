package swm.s3.coclimb.api.adapter.in.web.media;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swm.s3.coclimb.api.adapter.in.web.media.dto.*;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.application.port.in.media.MediaCommand;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaDeleteRequestDto;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaPageRequestDto;
import swm.s3.coclimb.api.exception.FieldErrorType;
import swm.s3.coclimb.api.exception.errortype.ValidationFail;
import swm.s3.coclimb.config.argumentresolver.LoginUser;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.user.User;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class MediaController {

    private final MediaQuery mediaQuery;
    private final MediaCommand mediaCommand;

    @PostMapping("/medias")
    public ResponseEntity<Void> createMedia(@RequestBody @Valid MediaCreateRequest mediaCreateRequest, @LoginUser User user) {
        mediaCommand.createMedia(mediaCreateRequest.toServiceDto(user));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/medias/instagram/my-videos")
    public ResponseEntity<InstagramMyVideosResponse> getMyInstagramVideos(@LoginUser User user) {
        List<InstagramMediaResponseDto> myInstagramVideos = mediaQuery.getMyInstagramVideos(user.getInstagramUserInfo().getAccessToken());
        return ResponseEntity.ok(InstagramMyVideosResponse.of(myInstagramVideos));
    }

    @GetMapping("/medias")
    public ResponseEntity<MediaPageResponse> getAllMedias(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "null") String gymName) {
        if (page < 0) {
            throw ValidationFail.onRequest()
                    .addField("page", FieldErrorType.MIN(0));
        }

        Page<Media> pagedMedias;

        switch(gymName) {
            case "null":
                pagedMedias = mediaQuery.getPagedMedias(MediaPageRequestDto.builder()
                        .page(page)
                        .size(size)
                        .build());
                break;
            default:
                pagedMedias = mediaQuery.getPagedMediasByGymName(gymName, MediaPageRequestDto.builder()
                        .page(page)
                        .size(size)
                        .build());
                break;
        }

        return ResponseEntity.ok(MediaPageResponse.of(pagedMedias));
    }

    @GetMapping("/medias/me")
    public ResponseEntity<MediaPageResponse> getMyMedias(@LoginUser User user,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            throw ValidationFail.onRequest()
                    .addField("page", FieldErrorType.MIN(0));
        }

        Page<Media> pagedMedias = mediaQuery.getPagedMediasByUserId(user.getId(), MediaPageRequestDto.builder()
                .page(page)
                .size(size)
                .build());

        return ResponseEntity.ok(MediaPageResponse.of(pagedMedias));
    }

    @GetMapping("/medias/{id}")
    public ResponseEntity<MediaInfoResponse> getMediaInfo(@PathVariable Long id) {
        Media media = mediaQuery.getMediaById(id);
        return ResponseEntity.ok(MediaInfoResponse.of(media));
    }

    @DeleteMapping("/medias/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id, @LoginUser User user) {
        mediaCommand.deleteMedia(MediaDeleteRequestDto.builder()
                .mediaId(id)
                .user(user)
                .build());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/medias/{id}")
    public ResponseEntity<Void> updateMedia(@PathVariable Long id,
                                            @RequestBody @Valid MediaUpdateRequest mediaUpdateRequest,
                                            @LoginUser User user) {

        mediaCommand.updateMedia(mediaUpdateRequest.toServiceDto(id, user));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
