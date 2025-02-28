package com.lucas.pedido_ms.controller.order;

import com.lucas.pedido_ms.domains.order.Order;
import com.lucas.pedido_ms.domains.order.dto.CreateOrderDto;
import com.lucas.pedido_ms.domains.order.dto.DeleteOrderDto;
import com.lucas.pedido_ms.domains.order.dto.UpdateOrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IOrderController {

    @Operation(summary = "Cria um pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Order> create(@Valid CreateOrderDto createOrderDto) throws Exception;

    @Operation(summary = "Atualiza um pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Order> update(@Valid UpdateOrderDto orderDto) throws Exception;

    @Operation(summary = "Cancela / deleta um pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    ResponseEntity<Void> delete(@Valid DeleteOrderDto dto) throws Exception;

    @Operation(summary = "Pega todos os pedidos no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido buscados com sucesso"),
    })
    ResponseEntity<List<Order>> get() throws Exception;
}
