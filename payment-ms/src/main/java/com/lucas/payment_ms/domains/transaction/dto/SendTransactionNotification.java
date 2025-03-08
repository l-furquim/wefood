package com.lucas.payment_ms.domains.transaction.dto;

public record SendTransactionNotification(
        String userToBeNotified,
        String content,
        Long orderId,
        String type
) {
}
