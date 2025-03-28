package com.lucas.pedido_ms.services;

import com.lucas.pedido_ms.client.IRestaurantClient;
import com.lucas.pedido_ms.domains.restaurant.dto.RestaurantDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IRestaurantService  {

    private static final Logger log = LoggerFactory.getLogger(IRestaurantService.class);
    private final IRestaurantClient restaurantClient;

    public IRestaurantService(IRestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    public ResponseEntity<RestaurantDto> getById(Long id) {
        try{
            return restaurantClient.getById(id);
        }catch (Exception e){
            log.error("Erro ao buscar o restaurant pelo id no client " + e.getMessage());
        }
        return null;
    }
}
