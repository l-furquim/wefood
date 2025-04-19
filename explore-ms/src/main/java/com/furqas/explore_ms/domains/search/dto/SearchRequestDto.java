package com.furqas.explore_ms.domains.search.dto;

import com.furqas.explore_ms.domains.search.enums.SearchCategory;

public record SearchRequestDto(
        String content,
        String userId,
        SearchCategory category
) {
}
