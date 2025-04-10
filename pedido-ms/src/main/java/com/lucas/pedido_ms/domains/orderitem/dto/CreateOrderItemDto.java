package com.lucas.pedido_ms.domains.orderitem.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateOrderItemDto(
        CreateOrderItemMetadataDto data,
        MultipartFile image
) {
}
