package com.lucas.profile_ms.controller.restaurant.impl;


import com.lucas.profile_ms.controller.restaurant.IRestaurantController;
import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantMetadataDto;
import com.lucas.profile_ms.domains.restaurant.dto.GetRestaurantImagesDto;
import com.lucas.profile_ms.services.IRestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/api/restaurants")
public class RestaurantControllerImpl implements IRestaurantController {

    private final IRestaurantService restaurantService;

    public RestaurantControllerImpl(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Restaurant> createConfirmation(
            @RequestPart CreateRestaurantMetadataDto data,
            @RequestPart MultipartFile image

    ){
        var restaurant = restaurantService.createConfirmation(new CreateRestaurantDto(
                data,
                image
        ));
        return ResponseEntity.status(201).body(restaurant);
    }

    @GetMapping("/confirm/{code}/{email}")
    public ResponseEntity<String> confirmCode(@PathVariable("code") String code, @PathVariable("email") String email){
        restaurantService.confirmCode(new ConfirmCodeDto(email,code));

        return ResponseEntity.status(201).body("Codigo confirmado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll(){
        return ResponseEntity.ok().body(restaurantService.getAll());
    }

    @GetMapping("/email/{domainEmail}")
    public ResponseEntity<List<Restaurant>> getEmail(@PathVariable("domainEmail")String domainEmail){
        var accounts = restaurantService.getAllBranchAccounts(domainEmail);

        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable("id") Long id){
        var restaurant = restaurantService.findById(id);

        log.info("Restaurante encontrado: {}", restaurant);

        if(restaurant == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(restaurant);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<GetRestaurantImagesDto> getRestaurantImages(@PathVariable("id") Long id){
        var urls = restaurantService.getRestaurantImages(id);

        return ResponseEntity.ok().body(urls);
    }
}
