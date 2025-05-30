package com.lucas.pedido_ms.controllers.orderitem;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.CreateOrderItemMetadataDto;
import com.lucas.pedido_ms.domains.orderitem.dto.DeleteOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.FindByRestaurantDto;
import com.lucas.pedido_ms.domains.orderitem.dto.UpdateOrderItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public interface IOrderItemController {

    @Operation(summary = "Cria um item de pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<OrderItem> create(
            @RequestPart("orderItemMetaData") CreateOrderItemMetadataDto data,
            @RequestPart("orderItemImage") MultipartFile image
    ) throws Exception;

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

    @Operation(summary = "Pega todos os items de pedido relacionados a um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items encontrados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<List<FindByRestaurantDto>> getByRestaurantId(@PathVariable("restaurantId") Long restaurantId) throws Exception;

}
