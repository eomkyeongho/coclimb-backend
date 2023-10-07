package swm.s3.coclimb.api.adapter.out.persistence.search.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutoCompleteNameDto {

    private List<String> names;
    private String index;

    @Builder
    public AutoCompleteNameDto(List<String> names, String index) {
        this.names = names;
        this.index = index;
    }
}
