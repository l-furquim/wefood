package com.lucas.notification_ms.services.notification.impl;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;
import com.lucas.notification_ms.domains.notification.enums.NotificationType;
import com.lucas.notification_ms.domains.notification.exceptions.ErrorSendingNotification;
import com.lucas.notification_ms.domains.notification.exceptions.InvalidDataCreateNotificationException;
import com.lucas.notification_ms.domains.notification.exceptions.NotificationNotFoundException;
import com.lucas.notification_ms.repositories.NotificationRepository;
import com.lucas.notification_ms.services.notification.INotificationService;
import com.lucas.notification_ms.services.notification.ISseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Notification create(CreateNotificationDto data){
        if(data.content() == null ||
            data.content().isEmpty() ||
            data.orderId() == null ||
            data.userToBeNotified() == null
            ){
            throw new InvalidDataCreateNotificationException("Invalid data for creating a notification");
        }
        var notification = Notification
                .builder()
                .content(data.content())
                .userId(data.userToBeNotified())
                .createdAt(LocalDateTime.now())
                .orderId(data.orderId())
                .type(data.type())
                .build();

        try{
            iSseService.sendNotification(data.userToBeNotified(), data.type(), data.content());
        }catch (Exception e){
            throw new ErrorSendingNotification(e.getMessage());
        }

        return notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void delete(DeleteNotificationDto data) {
        if(data.id() == null){
            throw new InvalidDataCreateNotificationException("Invalid data for creating a notification");
        }

        var notification = notificationRepository.findById(data.id());

        if(notification.isEmpty()){
            throw new NotificationNotFoundException("Notification not found during the deletion");
        }

        notificationRepository.delete(notification.get());

    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
}
