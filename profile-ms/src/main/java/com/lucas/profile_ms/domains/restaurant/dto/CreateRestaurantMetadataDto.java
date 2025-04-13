package com.lucas.profile_ms.domains.restaurant.dto;

public record CreateRestaurantMetadataDto(
        String name,
        String domainEmail,
        String password,
        String cnpj,
        String address,
        Boolean isMainAccount
) {
}
