package com.lucas.pedido_ms.domains.orderitem.dto;

import com.lucas.pedido_ms.domains.order.Order;
import com.lucas.pedido_ms.domains.orderitem.OrderItem;

import java.math.BigDecimal;

public record CreateOrderItemDto(
        String title,
        String description,
        Long quantity,
        BigDecimal price,
        Order order,
        String userId
    ) {
}
