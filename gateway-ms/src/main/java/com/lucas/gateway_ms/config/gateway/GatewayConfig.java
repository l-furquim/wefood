package com.lucas.gateway_ms.config.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class GatewayConfig {

//
//    private final AuthenticationFilter authenticationFilter;
//
//    public GatewayConfig(AuthenticationFilter authenticationFilter) {
//        this.authenticationFilter = authenticationFilter;
//    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route("payment-ms-transactions", r -> r.path("/v1/api/transactions/**")
////                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://payment-ms"))
//                .route("payment-ms-accounts", r -> r.path("/v1/api/accounts/**")
////                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://payment-ms"))
                .route("profile-ms", r -> r.path("/v1/api/profiles/**")
                        //.filters(f -> f.filter(authenticationFilter))
                        .uri("lb://profile-ms"))
                .build();
    }
}
