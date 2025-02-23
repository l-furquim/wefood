package com.lucas.pedido_ms.domains.order.dto;

public record AddressDto(
        Long id,
        String street,
        String number,
        String state,
        String zip,
        String userId
) {
}
