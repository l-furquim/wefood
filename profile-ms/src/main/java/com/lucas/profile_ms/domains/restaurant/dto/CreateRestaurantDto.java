package com.lucas.profile_ms.domains.restaurant.dto;

public record CreateRestaurantDto(
        String name,
        String domainEmail,
        String password,
        String cnpj,
        String address,
        Boolean isMainAccount
) {
}
