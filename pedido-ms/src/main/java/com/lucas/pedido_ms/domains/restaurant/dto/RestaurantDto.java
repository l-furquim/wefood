package com.lucas.pedido_ms.domains.restaurant.dto;

public record RestaurantDto(
        Long id,
        String name,
        String cnpj,
        String domainEmail,
        String address
) {
}
