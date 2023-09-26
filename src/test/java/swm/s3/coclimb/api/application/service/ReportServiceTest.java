package swm.s3.coclimb.api.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.application.port.in.report.dto.ReportCreateRequestDto;
import swm.s3.coclimb.domain.report.Report;

import static org.assertj.core.api.Assertions.assertThat;

class ReportServiceTest extends IntegrationTestSupport {

    @Test
    @DisplayName("제보를 등록할 수 있다.")
    void createReport() {
        //given
        ReportCreateRequestDto request = ReportCreateRequestDto.builder().description("desctiption").build();

        //when
        reportService.createReport(request);
        Report sut = reportJpaRepository.findAll().get(0);

        //then
        assertThat(sut).isNotNull();
        assertThat(sut.getDescription()).isEqualTo("desctiption");
    }
}