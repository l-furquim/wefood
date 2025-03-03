package com.lucas.pedido_ms.domains.order.dto;

import java.math.BigDecimal;

public record SendOrderPaymentRequestDto (
        String userId,
        BigDecimal price,
        String originKey
) {
}
