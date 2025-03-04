package com.lucas.notification_ms.services.notification.impl;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;
import com.lucas.notification_ms.domains.notification.enums.NotificationType;
import com.lucas.notification_ms.domains.notification.exceptions.InvalidDataCreateNotificationException;
import com.lucas.notification_ms.services.notification.INotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements INotificationService {
    @Override
    public Notification create(CreateNotificationDto data) {
        if(data.content() == null ||
            data.content().isEmpty() ||
            data.orderId() == null ||
            data.userId() == null ||
            !data.type().equals(NotificationType.ORDER) ||
            !data.type().equals(NotificationType.PAYMENT)){
            throw new InvalidDataCreateNotificationException("Invalid data for creating a notification");
        }

        var not
    }

    @Override
    public void delete(DeleteNotificationDto data) {

    }

    @Override
    public List<Notification> getAll() {
        return List.of();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return Optional.empty();
    }
}
