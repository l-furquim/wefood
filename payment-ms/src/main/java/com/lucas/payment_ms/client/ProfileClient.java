package com.lucas.payment_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("profile-ms")
public interface ProfileClient {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/profiles/{userId}")
    ResponseEntity<String> getUserEmail(@PathVariable("userId") String userId, @RequestHeader("Authorization") String token);
}
