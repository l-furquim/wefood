package com.furqas.explore_ms.config.client;

import com.furqas.explore_ms.domains.orderitemclient.dto.GetOrderItemByNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "orderItemClient", url = "${feign.client.orderItem.url}")
public interface IOrderItemClient {

    @GetMapping("/orderitems/{name}")
    public ResponseEntity<List<GetOrderItemByNameDto>> getOrderItemByName(@PathVariable("name") String name);

}
