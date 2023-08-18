package swm.s3.coclimb.api.application.port.in.media.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MediaPageRequestDto {
    private int page;
    private int size;
    private String sort;
}
