package com.lucas.profile_ms.controller.restaurant.impl;


import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantDto;
import com.lucas.profile_ms.services.IRestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/restaurants")
public class RestaurantControllerImpl {

    private final IRestaurantService restaurantService;

    public RestaurantControllerImpl(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody CreateRestaurantDto data){
        var restaurant = restaurantService.createConfirmation(data);

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

    @GetMapping("/{domainEmail}")
    public ResponseEntity<List<Restaurant>> getEmail(@PathVariable("domainEmail")String domainEmail){
        var accounts = restaurantService.getAllBranchAccounts(domainEmail);

        return ResponseEntity.ok().body(accounts);
    }
}
