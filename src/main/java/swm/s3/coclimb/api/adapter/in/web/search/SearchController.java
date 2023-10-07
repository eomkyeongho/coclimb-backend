package swm.s3.coclimb.api.adapter.in.web.search;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swm.s3.coclimb.api.adapter.in.web.search.dto.SearchNameResponse;
import swm.s3.coclimb.api.application.port.in.search.SearchQuery;
import swm.s3.coclimb.api.exception.FieldErrorType;
import swm.s3.coclimb.api.exception.errortype.ValidationFail;
import swm.s3.coclimb.domain.document.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchQuery searchQuery;

    @GetMapping("/search/autocomplete")
    public ResponseEntity<List<SearchNameResponse>> autoCompleteGymNames(
            @RequestParam @NotNull(message = "keyword is required") String keyword
            , @RequestParam @NotEmpty(message = "at least one search target is required") int[] targets) {
        List<Document> documents = getDocuments(targets);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(searchQuery.autoComplete(keyword, documents, 10)
                        .stream()
                        .map(SearchNameResponse::of)
                        .collect(Collectors.toList()));
    }

    private List<Document> getDocuments(int[] searchTargets) {
        Set<Document> documents = new HashSet<>();

        for (int target : searchTargets) {
            switch (target){
                case 0:
                    documents.add(Document.GYM);
                    break;
                case 1:
                    documents.add(Document.USER);
                    break;
                default:
                    throw ValidationFail.onRequest()
                            .addField("targets", target + FieldErrorType.INVALID_VALUE);
            }
        }
        return documents.stream().toList();
    }

}
