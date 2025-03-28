package com.lucas.pedido_ms.domains.orderitem.dto;


import java.math.BigDecimal;

public record CreateOrderItemDto(
        String title,
        String description,
        Long quantity,
        BigDecimal price,
        Long restaurantId,
        String userId
    ) {
}
