package com.lucas.pedido_ms.controller.orderitem;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.CreateOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.DeleteOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.UpdateOrderItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IOrderItemController {

    @Operation(summary = "Cria um item de pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<OrderItem> create(@Valid CreateOrderItemDto data) throws Exception;

    @Operation(summary = "Atualiza um item de pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item de pedido não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<OrderItem> update(@Valid UpdateOrderItemDto data) throws Exception;

    @Operation(summary = "Apaga um item de pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de pedido não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<Void> delete(@Valid DeleteOrderItemDto data) throws Exception;

    @Operation(summary = "Pega todos os items de pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items encontrados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<List<OrderItem>> get() throws Exception;

}
