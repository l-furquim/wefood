package com.lucas.notification_ms.services.notification.impl;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;
import com.lucas.notification_ms.domains.notification.enums.NotificationType;
import com.lucas.notification_ms.domains.notification.exceptions.InvalidDataCreateNotificationException;
import com.lucas.notification_ms.repositories.NotificationRepository;
import com.lucas.notification_ms.services.notification.INotificationService;
import com.lucas.notification_ms.services.notification.ISseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements INotificationService {

    private final ISseService iSseService;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(ISseService iSseService, NotificationRepository notificationRepository) {
        this.iSseService = iSseService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(CreateNotificationDto data) {
        if(data.content() == null ||
            data.content().isEmpty() ||
            data.orderId() == null ||
            data.userToBeNotified() == null ||
            !data.type().equals(NotificationType.ORDER) ||
            !data.type().equals(NotificationType.PAYMENT)){
            throw new InvalidDataCreateNotificationException("Invalid data for creating a notification");
        }
        var notification = new Notification(
                data.content(),
                data.userToBeNotified(),
                LocalDateTime.now(),
                data.orderId(),
                data.type()
        );


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
