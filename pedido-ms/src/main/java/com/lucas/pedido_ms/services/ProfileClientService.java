package com.lucas.pedido_ms.services;

import com.lucas.pedido_ms.client.IProfileClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileClientService {

    private static final Logger log = LoggerFactory.getLogger(ProfileClientService.class);
    private final RedisTemplate<String, String> redisTemplate;
    private final IProfileClient profileClient;

    public ProfileClientService(RedisTemplate<String, String> redisTemplate, IProfileClient profileClient) {
        this.redisTemplate = redisTemplate;
        this.profileClient = profileClient;
    }

    public String getUserEmail(String userId){
        var email = "";

        try{
            String token = redisTemplate.opsForValue().get(userId);

            log.info("Token recebido do redis: {}", token);

            var body = profileClient.getUserEmail(userId, token).getBody();
            log.info("Response recebida do client {}",body);

            email = body;

        }catch (Exception e) {
            log.info("Erro ao buscar o email no client " + e.getMessage());
        }
        return email;
    }

}
