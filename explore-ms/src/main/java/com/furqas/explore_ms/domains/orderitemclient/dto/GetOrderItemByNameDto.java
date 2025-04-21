package com.furqas.explore_ms.domains.orderitemclient.dto;

import java.math.BigDecimal;

public record GetOrderItemByNameDto(
        String title,
        BigDecimal price,
        String description
) {
}
