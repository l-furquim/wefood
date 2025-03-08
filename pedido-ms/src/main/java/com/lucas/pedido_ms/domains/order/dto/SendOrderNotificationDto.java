package com.lucas.pedido_ms.domains.order.dto;

public record SendOrderNotificationDto(
        String userToBeNotified,
        String content,
        Long orderId,
        String type
) {
}
