package com.furqas.explore_ms.config.client;

import com.furqas.explore_ms.domains.profileclient.dto.GetRestaurantByNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "profileClient", url = "%{feign.client.profile.url}")
public interface IRestaurantClient {

    @GetMapping("/restaurants/{name}")
    public ResponseEntity<List<GetRestaurantByNameDto>> getRestaurantByName(@PathVariable("name") String name);

}
