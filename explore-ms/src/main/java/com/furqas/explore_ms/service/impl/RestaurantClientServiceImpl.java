package com.furqas.explore_ms.service.impl;

import com.furqas.explore_ms.config.client.IRestaurantClient;
import com.furqas.explore_ms.domains.profileclient.dto.GetRestaurantByNameDto;
import com.furqas.explore_ms.service.IRestaurantClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RestaurantClientServiceImpl implements IRestaurantClientService {

    private final IRestaurantClient client;

    public RestaurantClientServiceImpl(IRestaurantClient client) {
        this.client = client;
    }

    @Override
    public List<GetRestaurantByNameDto> getRestaurantByName(String name) {
        try{

            var restaurant =  client.getRestaurantByName(name).getBody();

            return restaurant;
        }catch (Exception e){
            log.error("Erro ao realizar requisicao para o client de restaurant " + e.getMessage());
        }
        return null;
    }
}
