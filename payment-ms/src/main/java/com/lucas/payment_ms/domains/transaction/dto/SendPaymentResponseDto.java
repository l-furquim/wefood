package com.lucas.payment_ms.domains.transaction.dto;

public record SendPaymentResponseDto(
        boolean validPayment,
        Long orderId
) {
}
