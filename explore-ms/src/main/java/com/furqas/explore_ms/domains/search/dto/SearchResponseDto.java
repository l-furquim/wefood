package com.furqas.explore_ms.domains.search.dto;

import com.furqas.explore_ms.domains.search.enums.SearchCategory;

public record SearchResponseDto(
        String content,
        SearchCategory category
) {
}
