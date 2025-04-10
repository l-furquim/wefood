package com.lucas.pedido_ms.domains.orderitem.dto;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;

import java.util.List;

public record FindByRestaurantDto(
        OrderItem order,
        List<String> orderItemImagePath
) {
}
