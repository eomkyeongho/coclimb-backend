package swm.s3.coclimb.api.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramRestApiManager;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class MediaServiceTest extends IntegrationTestSupport {
    MediaService mediaService;

    @Mock
    InstagramRestApiManager instagramRestApiManager;

    @BeforeEach
    void setUp() {
        mediaService = new MediaService(instagramRestApiManager);
    }

    @Test
    @DisplayName("미디어 타입 중 VIDEO만 필터링하여 반환한다.")
    void getMyVideos() throws JsonProcessingException {
        //given
        given(instagramRestApiManager.getMyMedias(any(String.class))).willReturn(List.of(
                new TestInstagramMediaResponseDto("1", "VIDEO"),
                new TestInstagramMediaResponseDto("2", "IMAGE")
        ));
        //when
        List<InstagramMediaResponseDto> myVideos = mediaService.getMyInstagramVideos("accessToken");

        //then
        assertThat(myVideos).hasSize(1)
                .extracting("mediaId", "mediaType")
                .containsExactly(tuple("1", "VIDEO"));
    }

    private class TestInstagramMediaResponseDto extends InstagramMediaResponseDto{
        String mediaId;
        String mediaType;

        public TestInstagramMediaResponseDto() {
            super();
        }

        public TestInstagramMediaResponseDto(String mediaId, String mediaType) {
            this.mediaId = mediaId;
            this.mediaType = mediaType;
        }

        @Override
        public String getMediaId() {
            return mediaId;
        }

        @Override
        public String getMediaType() {
            return mediaType;
        }
    }
}