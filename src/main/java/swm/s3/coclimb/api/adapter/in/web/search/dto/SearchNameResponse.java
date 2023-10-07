package swm.s3.coclimb.api.adapter.in.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import swm.s3.coclimb.api.application.port.in.search.dto.SearchNameResult;
import swm.s3.coclimb.domain.document.Document;

@Getter
public class SearchNameResponse {
    private String name;
    private Integer type;

    @Builder
    public SearchNameResponse(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public static SearchNameResponse of(SearchNameResult searchNameResult) {
        return SearchNameResponse.builder()
                .name(searchNameResult.getName())
                .type(convertType(searchNameResult.getType()))
                .build();
    }

    private static Integer convertType(String type) {
        if (type.equals(Document.GYM.getIndex())) {return 0;}
        if (type.equals(Document.USER.getIndex())) {return 1;}

        return -1;
    }

}
