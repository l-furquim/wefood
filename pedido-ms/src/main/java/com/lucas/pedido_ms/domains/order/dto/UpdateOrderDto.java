package com.lucas.pedido_ms.domains.order.dto;

public record UpdateOrderDto(
        AddressDto address,
        Long id
) {
}
