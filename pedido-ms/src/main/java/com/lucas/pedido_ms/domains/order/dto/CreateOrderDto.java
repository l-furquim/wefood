package com.lucas.pedido_ms.domains.order.dto;
import java.util.List;

public record CreateOrderDto(
        String userId,
        List<OrderItemCreateOrderDto> items,
        AddressDto address
) {
}
