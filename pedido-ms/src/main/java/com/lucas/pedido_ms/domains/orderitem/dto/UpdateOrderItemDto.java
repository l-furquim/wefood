package com.lucas.pedido_ms.domains.orderitem.dto;

import com.lucas.pedido_ms.domains.order.Order;

import java.math.BigDecimal;

public record UpdateOrderItemDto(
        Long id,
        String title,
        String description,
        BigDecimal price,
        Long quantity,
        Order order
) {
}
