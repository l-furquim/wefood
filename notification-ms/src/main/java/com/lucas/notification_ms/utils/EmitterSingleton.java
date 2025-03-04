package com.lucas.notification_ms.utils;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

public class EmitterSingleton {

    private final Map<String, SseEmitter> emitters;
    private static EmitterSingleton instance;

    public static synchronized EmitterSingleton getInstance() {
        if (instance == null) {
            instance = new EmitterSingleton();
        }
        return instance;
    }

    private EmitterSingleton() {
        emitters = new HashMap<>();
    }

    public void put(String identifier, SseEmitter sseEmitter) {
        emitters.put(identifier, sseEmitter);
    }

    public SseEmitter get(String identifier) {
        return emitters.get(identifier);
    }

    public void remove(String identifier) {
        emitters.remove(identifier);
    }

}
