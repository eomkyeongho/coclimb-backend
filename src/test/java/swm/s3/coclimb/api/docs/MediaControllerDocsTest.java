package swm.s3.coclimb.api.docs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import swm.s3.coclimb.api.RestDocsTestSupport;
import swm.s3.coclimb.api.adapter.in.web.media.MediaController;
import swm.s3.coclimb.api.adapter.out.persistence.media.MediaJpaRepository;
import swm.s3.coclimb.api.adapter.out.persistence.user.UserJpaRepository;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.media.ProblemInfo;
import swm.s3.coclimb.domain.user.User;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MediaControllerDocsTest extends RestDocsTestSupport {
    @Autowired
    MediaController mediaController;
    @Autowired
    MediaJpaRepository mediaJpaRepository;
    @Autowired
    UserJpaRepository userJpaRepository;

    @BeforeEach
    void setUp() {
        mediaJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

//    @Test
//    @DisplayName("미디어를 등록할 수 있다.")
//    void createMedia() throws Exception {
//        //given
//        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
//                .willReturn(User.builder().instagramUserInfo(InstagramUserInfo.builder().build()).build());
//        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
//        willDoNothing().given(mediaCommand).createMedia(any());
//
//        MediaCreateRequest request = MediaCreateRequest.builder()
//                .mediaType("VIDEO")
//                .mediaUrl("mediaUrl")
//                .platform("platform")
//                .build();
//
//        //when
//        //then
//        mockMvc.perform(post("/medias")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated());
//    }

    @Test
    @DisplayName("전체 미디어를 페이징 조회할 수 있다.")
    void getPagedMedias() throws Exception {
        //given
        int pageSize = 5;

        mediaJpaRepository.saveAll(IntStream.range(0, 10).mapToObj(i -> Media.builder()
                        .thumbnailUrl("thumbnailUrl" + String.valueOf(i))
                        .problemInfo(ProblemInfo.builder()
                                .gymName("gym" + String.valueOf(i))
                                .color("color" + String.valueOf(i))
                                .build())
                        .build())
                .collect(Collectors.toList()));

        //when
        //then
        ResultActions result = mockMvc.perform(get("/medias")
                        .param("page", "0")
                        .param("size", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medias.length()").value(pageSize))
                .andExpect(jsonPath("$.medias[0].gymName").value("gym0"))
                .andExpect(jsonPath("$.medias[1].gymName").value("gym1"))
                .andExpect(jsonPath("$.medias[2].gymName").value("gym2"))
                .andExpect(jsonPath("$.medias[3].gymName").value("gym3"))
                .andExpect(jsonPath("$.medias[4].gymName").value("gym4"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.totalPage").value(2));

        //docs
        result.andDo(document("media-page",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 사이즈")
                ),
                responseFields(
                        fieldWithPath("page")
                                .type(JsonFieldType.NUMBER)
                                .description("페이지 번호"),
                        fieldWithPath("size")
                                .type(JsonFieldType.NUMBER)
                                .description("페이지 사이즈"),
                        fieldWithPath("totalPage")
                                .type(JsonFieldType.NUMBER)
                                .description("전체 페이지 수"),
                        fieldWithPath("medias")
                                .type(JsonFieldType.ARRAY)
                                .description("미디어 목록"),
                        fieldWithPath("medias[].id")
                                .type(JsonFieldType.NUMBER)
                                .description("미디어 ID"),
                        fieldWithPath("medias[].thumbnailUrl")
                                .type(JsonFieldType.STRING)
                                .description("미디어 썸네일 URL"),
                        fieldWithPath("medias[].gymName")
                                .type(JsonFieldType.STRING)
                                .description("미디어 내 암장 이름"),
                        fieldWithPath("medias[].problemColor")
                                .type(JsonFieldType.STRING)
                                .description("미디어 내 문제 난이도 색상")
                )));
    }
    @Test
    @DisplayName("내 미디어를 페이징 조회할 수 있다.")
    void getPagedMediasByUserId() throws Exception {
        //given
        int pageSize = 5;

        userJpaRepository.save(User.builder().build());
        Long userId = userJpaRepository.findAll().get(0).getId();

        mediaJpaRepository.saveAll(IntStream.range(0, 10).mapToObj(i -> Media.builder()
                        .userId(userId)
                        .thumbnailUrl("thumbnailUrl" + String.valueOf(i))
                        .problemInfo(ProblemInfo.builder()
                                .gymName("gym" + String.valueOf(i))
                                .color("color" + String.valueOf(i))
                                .build())
                        .build())
                .collect(Collectors.toList()));

        //when
        //then
        ResultActions result = mockMvc.perform(get("/medias/me")
                        .header("Authorization", jwtManager.issueToken(String.valueOf(userId)))
                        .param("page", "0")
                        .param("size", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medias.length()").value(pageSize))
                .andExpect(jsonPath("$.medias[0].gymName").value("gym0"))
                .andExpect(jsonPath("$.medias[1].gymName").value("gym1"))
                .andExpect(jsonPath("$.medias[2].gymName").value("gym2"))
                .andExpect(jsonPath("$.medias[3].gymName").value("gym3"))
                .andExpect(jsonPath("$.medias[4].gymName").value("gym4"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.totalPage").value(2));

        //docs
        result.andDo(document("my-media-page",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("JWT 인증 토큰")
                ),
                queryParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 사이즈")
                ),
                responseFields(
                        fieldWithPath("page")
                                .type(JsonFieldType.NUMBER)
                                .description("페이지 번호"),
                        fieldWithPath("size")
                                .type(JsonFieldType.NUMBER)
                                .description("페이지 사이즈"),
                        fieldWithPath("totalPage")
                                .type(JsonFieldType.NUMBER)
                                .description("전체 페이지 수"),
                        fieldWithPath("medias")
                                .type(JsonFieldType.ARRAY)
                                .description("미디어 목록"),
                        fieldWithPath("medias[].id")
                                .type(JsonFieldType.NUMBER)
                                .description("미디어 ID"),
                        fieldWithPath("medias[].thumbnailUrl")
                                .type(JsonFieldType.STRING)
                                .description("미디어 썸네일 URL"),
                        fieldWithPath("medias[].gymName")
                                .type(JsonFieldType.STRING)
                                .description("미디어 내 암장 이름"),
                        fieldWithPath("medias[].problemColor")
                                .type(JsonFieldType.STRING)
                                .description("미디어 내 문제 난이도 색상")
                )));
    }
}
