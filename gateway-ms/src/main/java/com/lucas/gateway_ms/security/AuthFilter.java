package com.lucas.gateway_ms.security;

import com.lucas.gateway_ms.service.TokenService;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GatewayFilter, Ordered {

    private final TokenService tokenService;
    private static final List<String> openUris = List.of(
            // Rotas do swagger
            "/v1/api-docs",
            "/v1/api-docs/swagger-config",
            "/swagger-ui.html",
            "/swagger-ui",

            // Rota para auth
            "/v1/api/profiles/auth",
            "/v1/api/profiles",
            "/v1/api/profile/confirm",

            // Rota para notificações
            "/v1/api/notifications"
    );

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(!openUris.contains(request.getURI().getPath())) {

            boolean isUserAuthenticated = request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);

            if(!isUserAuthenticated) {
                ServerHttpResponse response =  exchange.getResponse();

                response.setStatusCode(HttpStatus.UNAUTHORIZED);

                return response.setComplete();
            }

            final String token = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).replace("Bearer ", "");

            try {
                tokenService.isTokenValid(token);
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, token).build();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
