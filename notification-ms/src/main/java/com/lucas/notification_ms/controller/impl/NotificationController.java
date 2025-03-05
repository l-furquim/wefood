package com.lucas.notification_ms.controller.impl;

import com.lucas.notification_ms.controller.INotificationController;
import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;
import com.lucas.notification_ms.services.notification.INotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/notifications")
public class NotificationController implements INotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody CreateNotificationDto data){
        var notification = notificationService.create(data);

        return ResponseEntity.status(201).body(notification);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteNotificationDto data){
        notificationService.delete(data);

        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAll(){
        var notifications = notificationService.getAll();

        return ResponseEntity.ok().body(notifications);
    }

}
