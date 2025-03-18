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
        final String[] email = {""};

        new Thread(){
            @Override
            public void run(){
                try{
                    String token = redisTemplate.opsForValue().get(userId);


                    email[0] = profileClient.getUserEmail(userId, token).getBody();
                }catch (Exception e){
                    log.info("Erro ao buscar o email no client " + e.getMessage());
                }
            }

        }.start();

        return email[0];
    }

}
