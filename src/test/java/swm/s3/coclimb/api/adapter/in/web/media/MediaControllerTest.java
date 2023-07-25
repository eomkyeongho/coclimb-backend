package swm.s3.coclimb.api.adapter.in.web.media;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;
import swm.s3.coclimb.api.application.port.in.media.dto.MediaInfoDto;
import swm.s3.coclimb.config.WebConfig;
import swm.s3.coclimb.domain.Media;
import swm.s3.coclimb.interceptor.AutoLoginInterceptor;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MediaController.class)
@ActiveProfiles("test")
class MediaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MediaQuery mediaQuery;

    @MockBean
    WebConfig webConfig;

    @MockBean
    AutoLoginInterceptor autoLoginInterceptor;

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
                .andExpect(jsonPath("$.data.medias").isArray())
                .andExpect(jsonPath("$.data.medias.length()").value(2))
                .andExpect(jsonPath("$.data.medias[0].mediaType").value("VIDEO"))
                .andExpect(jsonPath("$.data.medias[1].mediaType").value("IMAGE"));
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
                .andExpect(jsonPath("$.data.medias").isArray())
                .andExpect(jsonPath("$.data.medias.length()").value(1))
                .andExpect(jsonPath("$.data.medias[0].mediaType").value("VIDEO"));
    }
}