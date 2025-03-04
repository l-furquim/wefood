package com.lucas.notification_ms.domains.notification.dto;


import com.lucas.notification_ms.domains.notification.enums.NotificationType;

public record CreateNotificationDto(
        String userId,
        String content,
        Long orderId,
        NotificationType type
) {
}
