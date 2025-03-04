package com.lucas.payment_ms.controller.transaction;

import com.lucas.payment_ms.domains.transaction.Transaction;
import com.lucas.payment_ms.domains.transaction.dto.CancelTransactionDto;
import com.lucas.payment_ms.domains.transaction.dto.CreateTransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ITransactionController {

    @Operation(summary = "Cria uma transação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para transação da conta"),
            @ApiResponse(responseCode = "401", description = "Ação realizada para a transação é invalida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Transaction> create(@Valid CreateTransactionDto data) throws Exception;

    @Operation(summary = "Cancela uma transação existente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transação cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para cancelamento da transação"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada para o cancelamento"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Void> delete(@Valid CancelTransactionDto data) throws Exception;

    @Operation(summary = "Busca todas as transações do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transações buscadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<List<Transaction>> getAll() throws Exception;
}
