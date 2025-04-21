package com.furqas.explore_ms.domains.search.dto;

import com.furqas.explore_ms.domains.orderitemclient.dto.GetOrderItemByNameDto;
import com.furqas.explore_ms.domains.profileclient.dto.GetRestaurantByNameDto;

import java.util.List;

public record SearchSomethingDto(
        List<GetRestaurantByNameDto> restaurants,
        List<GetOrderItemByNameDto> orderItems
) {
}
