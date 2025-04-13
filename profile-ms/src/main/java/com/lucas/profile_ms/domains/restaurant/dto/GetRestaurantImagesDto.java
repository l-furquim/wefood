package com.lucas.profile_ms.domains.restaurant.dto;

import com.lucas.profile_ms.domains.restaurant.Restaurant;

import java.util.List;

public record GetRestaurantImagesDto(
        List<String> imagesUrl
) {
}
