package swm.s3.coclimb.api.adapter.in.web.media;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import swm.s3.coclimb.api.ControllerTestSupport;
import swm.s3.coclimb.api.adapter.in.web.media.dto.MediaCreateRequest;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaInfoDto;
import swm.s3.coclimb.domain.Media;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MediaController.class)
@ActiveProfiles("test")
class MediaControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("모든 미디어를 조회한다.")
    void getAllMedias() throws Exception {
        //given
        given(mediaQuery.findAll()).willReturn(List.of(
                MediaInfoDto.of(Media.builder().mediaType("VIDEO").build()),
                MediaInfoDto.of(Media.builder().mediaType("IMAGE").build())
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
                MediaInfoDto.of(Media.builder().mediaType("VIDEO").build())
        ));

        //when
        //then
        mockMvc.perform(get("/medias").param("mediaType", "VIDEO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medias").isArray())
                .andExpect(jsonPath("$.medias.length()").value(1))
                .andExpect(jsonPath("$.medias[0].mediaType").value("VIDEO"));
    }

    @Test
    @DisplayName("미디어를 등록할 수 있다.")
    void createMedia() throws Exception {
        //given
        MediaCreateRequest request = MediaCreateRequest.builder()
                .userId(1L)
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