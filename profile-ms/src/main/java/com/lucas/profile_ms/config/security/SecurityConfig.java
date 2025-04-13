package com.lucas.profile_ms.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas públicas (sem autenticação)
                        .requestMatchers(
                                HttpMethod.POST, "/v1/api/profiles"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST, "/v1/api/restaurants"
                                // Autenticação (login)
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST, "/v1/api/restaurants/confirm/**"
                                // Autenticação (login)
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET, "/v1/api/profiles/confirm/{code}/{email}"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST, "/v1/api/profiles/auth"
                        ).permitAll()
                        // Rotas do Swagger/OpenAPI
                        .requestMatchers(
                                "/swagger-ui/**",      // Interface do Swagger
                                "/v3/api-docs/**",     // Documentação JSON
                                "/swagger-ui.html"     // Página HTML do Swagger
                        ).permitAll()
                        // Todas as outras rotas exigem autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
