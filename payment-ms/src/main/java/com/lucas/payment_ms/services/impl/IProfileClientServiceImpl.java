package com.lucas.payment_ms.services.impl;

import com.lucas.payment_ms.client.ProfileClient;
import com.lucas.payment_ms.services.IProfileClientService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IProfileClientServiceImpl implements IProfileClientService {

    private final ProfileClient profileClient;
    private final RedisTemplate<String, String> redisTemplate;

    public IProfileClientServiceImpl(ProfileClient profileClient, RedisTemplate redisTemplate) {
        this.profileClient = profileClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getEmail(String userId) {
        var email = "";

        try{
            String token = redisTemplate.opsForValue().get(userId);

            email = profileClient.getUserEmail(userId, token).getBody();
        }catch (Exception e){
            log.error("Erro ao buscar o email do usuario no payment-ms {}", e.getMessage());
        }

        return email;
    }
}
