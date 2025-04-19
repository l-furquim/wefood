package com.furqas.explore_ms.service.search;

import com.furqas.explore_ms.domains.search.dto.*;

public interface ISearchService {

    void createSearch(SearchRequestDto request);
    void deleteSearchFromHistory(DeleteSearchFromHistoryDto request);
    GetAllUserSearchesDto getAllByUser(String userId);
    GetRecentSearchesDto getRecent(String userId);
}
