package com.lucas.notification_ms.services.notification;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;
import com.lucas.notification_ms.domains.notification.exceptions.ErrorSendingNotification;

import java.util.List;
import java.util.Optional;

public interface INotificationService {

    Notification create(CreateNotificationDto data) throws ErrorSendingNotification;
    void delete(DeleteNotificationDto data);
    List<Notification> getAll();
    Notification findById(Long id);
}
