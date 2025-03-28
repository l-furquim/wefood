package com.lucas.profile_ms.controller.profile;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.AuthProfileDto;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public interface IProfileController {

    @Operation(summary = "Cria um perfil no sistema e envia o código de confirmação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil foi criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para criação do perfil"),
            @ApiResponse(responseCode = "401", description = "Um perfil ja existe com os dados fornecidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Profile> createProfileConfirmation(@Valid CreateProfileDto data) throws Exception;


    @Operation(summary = "Valida o código enviado por email, e confirma a criação da conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil foi validado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe nehuma pendencia com essa conta"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<String> confirmCode(@PathVariable("code") String code, @PathVariable("email") String email) throws Exception;

    @Operation(summary = "Autentica o usuario com base no email e na senha, retornando o token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil foi validado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe nehuma pendencia com essa conta"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<String> auth(@RequestBody AuthProfileDto data) throws Exception;
}
