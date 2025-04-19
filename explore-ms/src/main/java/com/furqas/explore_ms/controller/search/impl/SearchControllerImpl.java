package com.furqas.explore_ms.controller.search.impl;

import com.furqas.explore_ms.controller.search.ISearchController;
import com.furqas.explore_ms.domains.search.dto.GetAllUserSearchesDto;
import com.furqas.explore_ms.domains.search.dto.GetRecentSearchesDto;
import com.furqas.explore_ms.domains.search.dto.SearchRequestDto;
import com.furqas.explore_ms.service.search.ISearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/search")
public class SearchControllerImpl implements ISearchController {

    private final ISearchService service;

    public SearchControllerImpl(ISearchService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createSearch(@RequestBody SearchRequestDto request){
        service.createSearch(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/recent/{userId}")
    public ResponseEntity<GetRecentSearchesDto> getRecentSearches(@PathVariable("userId") String userId){
        var searches = service.getRecent(userId);


        return ResponseEntity.ok().body(searches);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<GetAllUserSearchesDto> getAllUserSearches(@PathVariable("userId") String userId){
        var searches = service.getAllByUser(userId);


        return ResponseEntity.ok().body(searches);
    }

}
