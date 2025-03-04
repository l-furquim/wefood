package com.lucas.notification_ms.controller.impl;

import com.lucas.notification_ms.services.notification.ISseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/v1/sse")
public class SseController {

    private final ISseService sseService;

    public SseController(ISseService sseService) {
        this.sseService = sseService;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@RequestParam("identifier") String identifier){
        return ResponseEntity.ok().body(sseService.subscribe(identifier));
    }
}
