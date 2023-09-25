package swm.s3.coclimb.api.application.port.in.report.dto;


import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.domain.report.Report;
import swm.s3.coclimb.domain.user.User;

@Getter
public class ReportCreateRequestDto {
    User user;
    String subject;
    String description;
    String target;

    @Builder
    public ReportCreateRequestDto(User user, String subject, String description, String target) {
        this.user = user;
        this.subject = subject;
        this.description = description;
        this.target = target;
    }

    public Report toEntity() {
        return Report.builder()
                .user(user)
                .subject(subject)
                .target(target)
                .description(description)
                .build();
    }
}
