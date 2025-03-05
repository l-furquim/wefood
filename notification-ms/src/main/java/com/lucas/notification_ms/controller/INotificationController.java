package com.lucas.notification_ms.controller;

import com.lucas.notification_ms.domains.notification.Notification;
import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.dto.DeleteNotificationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Validated
public interface INotificationController {

    @Operation(summary = "Cria uma notificacao e envia ela")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para criação da notificação"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Notification> create(@Valid CreateNotificationDto data) throws Exception;

    @Operation(summary = "Apaga uma notificacao do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificação apagada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para a deleção da notificação"),
            @ApiResponse(responseCode = "404", description = "Notificação não foi encontrada para a deleção", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Void> delete(@Valid DeleteNotificationDto data) throws Exception;

    @Operation(summary = "Busca todas notificações do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificações buscadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<List<Notification>> getAll() throws Exception;


}
