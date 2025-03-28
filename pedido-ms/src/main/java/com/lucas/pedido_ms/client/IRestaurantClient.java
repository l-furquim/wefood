package com.lucas.pedido_ms.client;

import com.lucas.pedido_ms.domains.restaurant.dto.RestaurantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="restaurantClient",url = "${feign.client.profile.url}")
public interface IRestaurantClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/api/restaurants/{id}")
    ResponseEntity<RestaurantDto> getById(@PathVariable("id") Long id);

}
