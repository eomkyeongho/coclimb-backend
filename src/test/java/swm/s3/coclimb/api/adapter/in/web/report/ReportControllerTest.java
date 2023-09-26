package swm.s3.coclimb.api.adapter.in.web.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.ControllerTestSupport;
import swm.s3.coclimb.api.adapter.in.web.report.dto.ReportCreateRequest;
import swm.s3.coclimb.domain.user.InstagramUserInfo;
import swm.s3.coclimb.domain.user.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("제보를 등록할 수 있다.")
    void createReport() throws Exception {
        //given
        String accessToken = "token";
        given(jwtManager.getSubject(accessToken)).willReturn("1");
        given(userLoadPort.getById(1L)).willReturn(User.builder()
                .name("username")
                .instagramUserInfo(InstagramUserInfo.builder().build())
                .build());
        ReportCreateRequest request = ReportCreateRequest.builder().build();
        willDoNothing().given(reportCommand).createReport(any());
        //when
        //then
        mockMvc.perform(post("/reports")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        then(reportCommand).should(times(1)).createReport(any());
    }
}