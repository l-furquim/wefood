package com.lucas.profile_ms.controller.restaurant;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantMetadataDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public interface IRestaurantController {


    @Operation(summary = "Cria uma conta de restaurant no sistema e envia o código de confirmação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Codigo enviado para confirmação com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para criação do restaurante"),
            @ApiResponse(responseCode = "401", description = "Um restaurante do tipo main ja existe com esse cnpj, ou ja foi enviado o pedido de confirmação da conta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<Restaurant> createConfirmation(
            @RequestPart CreateRestaurantMetadataDto data,
            @RequestPart MultipartFile image

    ) throws Exception;

    @Operation(summary = "Confirma a conta do restaurante a partir de um cdigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Codigo confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Codigo de confirmação não existe ou foi expirado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<String> confirmCode(@PathVariable("code") String code, @PathVariable("email") String email) throws Exception;

    @Operation(summary = "Busca todos os restaurantes presentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurantes buscados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<List<Restaurant>> getAll();


    @Operation(summary = "Busca as contas ramificadas de um restaurante a partir do domain email dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "O restaurante pai não existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<List<Restaurant>> getEmail(@PathVariable("domainEmail")String domainEmail) throws Exception;

    @Operation(summary = "Busca um restaurante a partir do id dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum restaurante encontrado com este id", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro não mapeado", content = @Content)
    })
    public ResponseEntity<Restaurant> getById(@PathVariable("id") Long id) throws Exception;

}
