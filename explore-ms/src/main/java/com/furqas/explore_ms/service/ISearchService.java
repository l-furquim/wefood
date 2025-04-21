package com.furqas.explore_ms.service;

import com.furqas.explore_ms.domains.search.dto.*;

public interface ISearchService {

    SearchSomethingDto createSearch(SearchRequestDto request);
    void deleteSearchFromHistory(DeleteSearchFromHistoryDto request);
    GetAllUserSearchesDto getAllByUser(String userId);
    GetRecentSearchesDto getRecent(String userId);
}
