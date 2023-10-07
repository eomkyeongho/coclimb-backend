package swm.s3.coclimb.api.application.service;

import org.springframework.stereotype.Service;
import swm.s3.coclimb.api.adapter.out.persistence.search.SearchManager;
import swm.s3.coclimb.api.adapter.out.persistence.search.dto.AutoCompleteNameDto;
import swm.s3.coclimb.api.application.port.in.search.SearchQuery;
import swm.s3.coclimb.api.application.port.in.search.dto.SearchNameResult;
import swm.s3.coclimb.domain.document.Document;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService implements SearchQuery {
    private final SearchManager searchManager;

    public SearchService(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Override
    public List<SearchNameResult> autoComplete(String keyword, List<Document> documents, int size) {

        List<AutoCompleteNameDto> response = searchManager.autoCompleteName(keyword, documents, size);
        int[] searchResultLens = response.stream().mapToInt(d -> d.getNames().size()).toArray();

        int threshold = 5;
        int left = getLeft(searchResultLens, threshold);

        return getSearchNameResults(response, left, threshold);

    }

    private List<SearchNameResult> getSearchNameResults(List<AutoCompleteNameDto> autoCompletedNames, int left, int threshold) {
        List<SearchNameResult> searchNameResults = new ArrayList<>();
        for (AutoCompleteNameDto autoCompleteNameDto : autoCompletedNames) {
            List<String> names = autoCompleteNameDto.getNames();
            for (int i = 0; i < Math.min(names.size(), threshold + left); i++) {
                searchNameResults.add(SearchNameResult.builder()
                        .name(names.get(i))
                        .type(autoCompleteNameDto.getIndex())
                        .build());
            }
        }
        return searchNameResults;
    }

    private int getLeft(int[] searchResultLens, int threshold) {
        int left =0;
        for (int i = 0; i < searchResultLens.length; i++) {
            if (searchResultLens[i] < threshold) {
                left += threshold - searchResultLens[i];
            }
        }
        return left;
    }


}
