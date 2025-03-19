package com.lucas.pedido_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

// Alterar o nome para o nome do ms do eureka e substituir url para path
@FeignClient(name="profileClient",url = "${feign.client.profile.url}")
public interface IProfileClient {

    @RequestMapping("/{userId}")
    public ResponseEntity<String> getUserEmail(@PathVariable("userId") String userId, @RequestHeader("Authorization") String token);
}
