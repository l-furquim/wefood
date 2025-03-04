package com.lucas.notification_ms.services.notification;

import com.lucas.notification_ms.domains.notification.enums.NotificationType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ISseService {

    SseEmitter subscribe(String identifier);
    void sendNotification(String userToBeNotified, NotificationType type, String content) throws Exception;
}
