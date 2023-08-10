package swm.s3.coclimb.api.adapter.in.web.media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import swm.s3.coclimb.api.ControllerTestSupport;
import swm.s3.coclimb.api.adapter.in.web.media.dto.MediaCreateRequest;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaInfoDto;
import swm.s3.coclimb.domain.media.InstagramMediaInfo;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.user.InstagramUserInfo;
import swm.s3.coclimb.domain.user.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MediaControllerTest extends ControllerTestSupport {
    @Autowired
    MediaController mediaController;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mediaController)
                .setCustomArgumentResolvers(loginUserArgumentResolver)
                .build();
    }

    @Test
    @DisplayName("모든 미디어를 조회한다.")
    void getAllMedias() throws Exception {
        //given
        given(mediaQuery.findAll()).willReturn(List.of(
                MediaInfoDto.of(Media.builder()
                        .instagramMediaInfo(InstagramMediaInfo.builder().build())
                        .mediaType("VIDEO").build()),
                MediaInfoDto.of(Media.builder()
                        .instagramMediaInfo(InstagramMediaInfo.builder().build())
                        .mediaType("IMAGE").build())
        ));

        //when
        //then
        mockMvc.perform(get("/medias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medias").isArray())
                .andExpect(jsonPath("$.medias.length()").value(2))
                .andExpect(jsonPath("$.medias[0].mediaType").value("VIDEO"))
                .andExpect(jsonPath("$.medias[1].mediaType").value("IMAGE"));
    }

    @Test
    @DisplayName("모든 VIDEO 타입의 미디어를 조회한다.")
    void getAllVideos() throws Exception {
        //given
        given(mediaQuery.findAllVideos()).willReturn(List.of(
                MediaInfoDto.of(Media.builder()
                        .mediaType("VIDEO")
                        .instagramMediaInfo(InstagramMediaInfo.builder().build())
                        .build()),
                MediaInfoDto.of(Media.builder()
                        .mediaType("VIDEO")
                        .instagramMediaInfo(InstagramMediaInfo.builder().build())
                        .build())
        ));

        //when
        //then
        mockMvc.perform(get("/medias").param("mediaType", "VIDEO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medias").isArray())
                .andExpect(jsonPath("$.medias.length()").value(2))
                .andExpect(jsonPath("$.medias[0].mediaType").value("VIDEO"))
                .andExpect(jsonPath("$.medias[1].mediaType").value("VIDEO"));
    }

    @Test
    @DisplayName("본인 미디어를 조회할 수 있다.")
    void getMyMedias() throws Exception {
        //given
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(User.builder().build());
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(mediaQuery.findMyMedias(any())).willReturn(List.of(
                MediaInfoDto.of(Media.builder()
                        .mediaType("VIDEO")
                        .instagramMediaInfo(InstagramMediaInfo.builder().build())
                        .build()),
                MediaInfoDto.of(Media.builder()
                        .mediaType("VIDEO")
                        .instagramMediaInfo(InstagramMediaInfo.builder().build())
                        .build())
        ));

        //when
        //then
        mockMvc.perform(get("/medias/my-medias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medias").isArray())
                .andExpect(jsonPath("$.medias.length()").value(2))
                .andExpect(jsonPath("$.medias[0].mediaType").value("VIDEO"))
                .andExpect(jsonPath("$.medias[1].mediaType").value("VIDEO"));
    }

    @Test
    @DisplayName("미디어를 등록할 수 있다.")
    void createMedia() throws Exception {
        //given
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(User.builder().instagramUserInfo(InstagramUserInfo.builder().build()).build());
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        willDoNothing().given(mediaCommand).createMedia(any());

        MediaCreateRequest request = MediaCreateRequest.builder()
                .mediaType("VIDEO")
                .mediaUrl("mediaUrl")
                .platform("platform")
                .build();

        //when
        //then
        mockMvc.perform(post("/medias")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}