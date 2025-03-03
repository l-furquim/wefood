package com.lucas.pedido_ms.domains.order.dto;

public record OrderPaymentConfirmedDto(
        boolean validPayment,
        Long orderId
) {
}
