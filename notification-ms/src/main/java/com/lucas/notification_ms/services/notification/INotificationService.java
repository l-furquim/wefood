package com.lucas.notification_ms.services.notification;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;

import java.util.List;
import java.util.Optional;

public interface INotificationService {

    Notification create(CreateNotificationDto data);
    void delete(DeleteNotificationDto data);
    List<Notification> getAll();
    Optional<Notification> findById(Long id);
}
