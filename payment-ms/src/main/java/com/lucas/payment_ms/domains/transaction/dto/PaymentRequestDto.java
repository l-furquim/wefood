package com.lucas.payment_ms.domains.transaction.dto;

import com.lucas.payment_ms.domains.transaction.enums.TransactionType;

import java.math.BigDecimal;

public record PaymentRequestDto(
        BigDecimal price,
        String userId,
        String restaurantId,
        TransactionType type,
        Long orderId
) {
}
