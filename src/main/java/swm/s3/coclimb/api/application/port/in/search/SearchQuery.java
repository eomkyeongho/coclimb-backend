package swm.s3.coclimb.api.application.port.in.search;

import swm.s3.coclimb.api.application.port.in.search.dto.SearchNameResult;
import swm.s3.coclimb.domain.document.Document;

import java.util.List;

public interface SearchQuery {
    List<SearchNameResult> autoComplete(String keyword, List<Document> targets, int size);
}
