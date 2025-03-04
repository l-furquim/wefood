package com.lucas.notification_ms.services.notification.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lucas.notification_ms.domains.notification.enums.NotificationType;
import com.lucas.notification_ms.services.notification.ISseService;
import com.lucas.notification_ms.utils.EmitterSingleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
public class SseServiceImpl implements ISseService {

    private final EmitterSingleton emitterSingleton;
    private static final Long EXPIRATION = 600_000L;
    private static final String EVENT_NAME = "wefood-notification";


    public SseServiceImpl() {
        this.emitterSingleton = EmitterSingleton.getInstance();
    }

    @Override
    public SseEmitter subscribe(String identifier) {
        SseEmitter emmiter = getEmitterInstance(identifier);

        emmiter.onCompletion(() -> {
            emitterSingleton.remove(identifier);
        });

        emmiter.onError((e) -> {
            emmiter.completeWithError(e);
            emitterSingleton.remove(identifier);
        });

        emmiter.onTimeout(() -> {
            emmiter.complete();
            emitterSingleton.remove(identifier);
        });
        return emmiter;
    }

    @Override
    public void sendNotification(String userToBeNotified, NotificationType type, String content) throws Exception {
        ObjectMapper obMapper = new ObjectMapper();
        ObjectNode obNode = obMapper.createObjectNode();

        obNode.put("type", type.toString());
        obNode.put("content", content);

        String jsonData = obMapper.writeValueAsString(obNode);
        var emitter = emitterSingleton.get(userToBeNotified);

        try {
            emitter.send(SseEmitter.event().name(EVENT_NAME).data(jsonData));
        } catch (Exception e) {
            emitter.completeWithError(e);
            emitterSingleton.remove(userToBeNotified);
        }
    }

    private SseEmitter getEmitterInstance(final String identifier){
        emitterSingleton.remove(identifier);

        var newEmitter = new SseEmitter(EXPIRATION);

        emitterSingleton.put(identifier, newEmitter);

        try{
            newEmitter.send(SseEmitter.event().name("OK").data("Connection ok"));
        }catch (Exception e){
            log.info("Erro ao estabelecer conexção sse: " + e.getMessage());
            newEmitter.completeWithError(e);
            emitterSingleton.remove(identifier);
        }

        return newEmitter;
    }
}
