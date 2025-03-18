package com.lucas.pedido_ms.domains.order.dto;

public record SendOrderMailDto(
        String to,
        String from,
        String subject,
        String content,
        String userId,
        String type
) {
}
