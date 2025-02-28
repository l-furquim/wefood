package com.lucas.pedido_ms.domains.order.dto;

import java.math.BigDecimal;

public record OrderOrderItemDto(
        Long id,
        String title,
        String description,
        Long quantity,
        BigDecimal price
){}
