package com.lucas.pedido_ms.domains.order.dto;

import com.lucas.pedido_ms.domains.order.enums.OrderStatus;

public record UpdateOrderStatusDto(
        Long orderId,
        OrderStatus status
) {
}
