package com.furqas.explore_ms.domains.search.dto;

import java.util.List;

public record GetAllUserSearchesDto(
        List<SearchResponseDto> searches
) {
}
