package com.lucas.payment_ms.controller.account;

import com.lucas.payment_ms.domains.account.Account;
import com.lucas.payment_ms.domains.account.dto.CreateAccountDto;
import com.lucas.payment_ms.domains.account.dto.DeleteAccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IAccountController {

    @Operation(summary = "Cria uma conta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para criação da conta"),
            @ApiResponse(responseCode = "401", description = "Uma conta ja existe com os dados fornecidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Account> create(@Valid CreateAccountDto data) throws Exception;

    @Operation(summary = "Apaga uma conta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta apagada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para deleção da conta"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada para deleção", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Void> delete(@Valid DeleteAccountDto data) throws Exception;

    @Operation(summary = "Mostra todas as contas do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<List<Account>> getAll() throws Exception;
}
