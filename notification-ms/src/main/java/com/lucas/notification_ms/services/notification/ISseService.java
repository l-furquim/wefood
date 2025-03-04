package com.lucas.notification_ms.services.notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ISseService {

    SseEmitter subscribe(String identifier);

}
