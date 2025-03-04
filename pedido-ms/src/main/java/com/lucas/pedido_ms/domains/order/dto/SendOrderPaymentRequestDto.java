package com.lucas.pedido_ms.domains.order.dto;

import java.math.BigDecimal;

public record SendOrderPaymentRequestDto (
        BigDecimal price,
        String userId,
        String restaurantId,
        String type,
        Long orderId
) {
}
