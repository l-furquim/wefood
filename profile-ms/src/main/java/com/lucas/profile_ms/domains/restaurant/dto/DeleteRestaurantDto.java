package com.lucas.profile_ms.domains.restaurant.dto;

public record DeleteRestaurantDto(
        String cnpj,
        String password
) {
}
