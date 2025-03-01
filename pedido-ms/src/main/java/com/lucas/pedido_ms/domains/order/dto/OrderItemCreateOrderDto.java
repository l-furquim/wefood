package com.lucas.pedido_ms.domains.order.dto;

import java.math.BigDecimal;

public record OrderItemCreateOrderDto(
        Long id,
        BigDecimal price,
        Long quantity
) {
}
