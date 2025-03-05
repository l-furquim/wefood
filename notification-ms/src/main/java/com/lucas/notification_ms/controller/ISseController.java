package com.lucas.notification_ms.controller;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Validated
public interface ISseController {


    @Operation(summary = "Cria a conexão com o sse da api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conexção estabelecida com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no estabelecimento da conexão", content = @Content)
    })
    ResponseEntity<SseEmitter> subscribe(@Valid String identifier) throws Exception;


}
