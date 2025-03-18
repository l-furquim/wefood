package com.lucas.payment_ms.domains.transaction.dto;

public record SendPaymentConfirmationEmailDto(
        String to,
        String from,
        String subject,
        String content,
        String userId,
        String type
) {
}
