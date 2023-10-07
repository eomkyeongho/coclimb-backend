package swm.s3.coclimb.api.application.port.in.search.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchNameResult {
    private String name;
    private String type; // todo Enum

    @Builder
    public SearchNameResult(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
