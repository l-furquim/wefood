package com.lucas.pedido_ms.domains.order.dto;

import java.util.List;

public record UpdateOrderDto(
        AddressDto address,
        List<OrderOrderItemDto> items,
        boolean remove,
        Long id
) {
}
