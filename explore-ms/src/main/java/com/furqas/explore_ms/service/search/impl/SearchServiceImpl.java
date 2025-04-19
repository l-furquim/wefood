package com.furqas.explore_ms.service.search.impl;

import com.furqas.explore_ms.domains.search.Search;
import com.furqas.explore_ms.domains.search.dto.*;
import com.furqas.explore_ms.domains.search.enums.SearchCategory;
import com.furqas.explore_ms.domains.search.exceptions.InvalidSearchDeletion;
import com.furqas.explore_ms.domains.search.exceptions.InvalidSearchRequest;
import com.furqas.explore_ms.domains.search.exceptions.SearchNotFound;
import com.furqas.explore_ms.repository.SearchRepository;
import com.furqas.explore_ms.service.search.ISearchService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SearchServiceImpl implements ISearchService {

    private final SearchRepository searchRepository;

    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Transactional
    @Override
    public void createSearch(SearchRequestDto request) {
        if
        (
            request.content() == null || request.content().isEmpty() || request.userId() == null ||
            request.userId().isEmpty()
        ){
            throw new InvalidSearchRequest("Encountered invalid data for creating a request");
        }

        var userRecentSearch = searchRepository.getByContentAndUserId(request.content(), request.userId());

        if(userRecentSearch.isPresent()){
            var search = userRecentSearch.get();

            search.setSearchedAt(
                    LocalDateTime.now()
            );

            searchRepository.save(search);

            return;
        }

        var search = Search.builder()
                .content(request.content())
                .searchedAt(LocalDateTime.now())
                .userId(request.userId())
                .category(request.category() == null ? SearchCategory.NONE : request.category())
                .build();

        searchRepository.save(search);
    }

    @Override
    public void deleteSearchFromHistory(DeleteSearchFromHistoryDto request) {

        if(request.id() == null || request.id() <= 0){
            throw new InvalidSearchDeletion("Cannot delete a search with a invalid id");
        }

        var search = searchRepository.findById(request.id());

        // Validar depois o id do usuario(to com preguiça)

        if(search.isEmpty()) throw new SearchNotFound("Could not found the search by this id");

        searchRepository.delete(search.get());
    }

    @Override
    public GetAllUserSearchesDto getAllByUser(String userId) {
        if(userId == null || userId.isEmpty()){
            throw new InvalidSearchRequest("Invalid userId " + userId);
        }

        var searches = searchRepository.getAllByUserId(userId);

        return new GetAllUserSearchesDto(
                searches.stream().map(
                        search -> new SearchResponseDto(
                                search.getContent(),
                                search.getCategory()
                        )
                ).toList()
        );
    }

    @Override
    public GetRecentSearchesDto getRecent(String userId) {
        if(userId == null || userId.isEmpty()){
            throw new InvalidSearchRequest("Invalid userId " + userId);
        }

        var searches = searchRepository.getRecentSearches(userId, PageRequest.of(0,8));

        return new GetRecentSearchesDto(
                searches.stream().map(
                        search -> new SearchResponseDto(
                                search.getContent(),
                                search.getCategory()
                        )
                ).toList()
        );
    }
}
