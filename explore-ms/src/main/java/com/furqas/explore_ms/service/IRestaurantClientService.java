package com.furqas.explore_ms.service;

import com.furqas.explore_ms.domains.profileclient.dto.GetRestaurantByNameDto;

import java.util.List;

public interface IRestaurantClientService {

    List<GetRestaurantByNameDto> getRestaurantByName(String name);

}
