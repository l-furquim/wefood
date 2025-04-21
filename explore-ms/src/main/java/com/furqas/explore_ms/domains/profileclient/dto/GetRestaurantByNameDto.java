package com.furqas.explore_ms.domains.profileclient.dto;

public record GetRestaurantByNameDto(
        String iconUrl,
        String name,
        String address
) {
}
