package com.lucas.pedido_ms.domains.order.dto;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;

import java.util.List;

public record CreateOrderDto(
        String userId,
        List<OrderItem> items,
        AddressDto address
) {
}
