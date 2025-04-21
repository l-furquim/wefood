package com.furqas.explore_ms.service.impl;

import com.furqas.explore_ms.domains.profileclient.dto.GetRestaurantByNameDto;
import com.furqas.explore_ms.domains.search.Search;
import com.furqas.explore_ms.domains.search.dto.*;
import com.furqas.explore_ms.domains.search.enums.SearchCategory;
import com.furqas.explore_ms.domains.search.exceptions.InvalidSearchDeletion;
import com.furqas.explore_ms.domains.search.exceptions.InvalidSearchRequest;
import com.furqas.explore_ms.domains.search.exceptions.SearchNotFound;
import com.furqas.explore_ms.repository.SearchRepository;
import com.furqas.explore_ms.service.IOrderItemClientService;
import com.furqas.explore_ms.service.IRestaurantClientService;
import com.furqas.explore_ms.service.ISearchService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {

    private final SearchRepository searchRepository;
    private final IRestaurantClientService restaurantClientService;
    private final IOrderItemClientService orderItemClientService;

    public SearchServiceImpl
            (
            SearchRepository searchRepository,
            IRestaurantClientService restaurantClientService,
            IOrderItemClientService orderItemClientService
    )
    {
        this.searchRepository = searchRepository;
        this.restaurantClientService = restaurantClientService;
        this.orderItemClientService = orderItemClientService;
    }

    @Transactional
    @Override
    public SearchSomethingDto createSearch(SearchRequestDto request) {
        if
        (
            request.content() == null || request.content().isEmpty() || request.userId() == null ||
            request.userId().isEmpty()
        ){
            throw new InvalidSearchRequest("Encountered invalid data for creating a request");
        }
        new Thread(() -> {
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
        }).start();

        List<GetRestaurantByNameDto> restaurantResponse = null;

        if(request.category().equals(SearchCategory.RESTAURANT)){
        }

        var orderItem = orderItemClientService.getOrderItemByName(request.content());

        if(restaurantResponse != null){
            return new SearchSomethingDto(
                    restaurantResponse,
                    orderItem
            );
        }

        restaurantResponse = restaurantClientService.getRestaurantByName(request.content());

        return new SearchSomethingDto(
                restaurantResponse,
                orderItem
        );

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
