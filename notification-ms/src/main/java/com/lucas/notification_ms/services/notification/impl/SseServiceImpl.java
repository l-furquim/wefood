package com.lucas.notification_ms.services.notification.impl;

import com.lucas.notification_ms.services.notification.ISseService;
import com.lucas.notification_ms.utils.EmitterSingleton;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseServiceImpl implements ISseService {

    private final EmitterSingleton emitterSingleton;
    private static final Long EXPIRATION = 600_000L;
    private static final String EVENT_NAME = "new-notification";

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

    private SseEmitter getEmitterInstance(final String identifier){
        emitterSingleton.remove(identifier);
        var newEmitter = new SseEmitter(EXPIRATION);
        emitterSingleton.put(identifier, newEmitter);

        try{
            newEmitter.send(SseEmitter.event().name("OK").data("Connection ok"));
        }catch (Exception e){
            newEmitter.completeWithError(e);
            emitterSingleton.remove(identifier);
        }

        return newEmitter;
    }
}
