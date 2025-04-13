package com.lucas.profile_ms.domains.restaurant.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateRestaurantDto(
        CreateRestaurantMetadataDto data,
        MultipartFile image
) {

}
