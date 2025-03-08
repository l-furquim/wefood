package com.lucas.profile_ms.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException{

        final var authToken = request.getHeader("Authorization");

        if(authToken != null){
            final var aToken = authToken.replace("Bearer ", "");

            final var userName = authService.validateToken(aToken);

            var user = authService.loadUserByUsername(userName);

            final var auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, httpServletResponse);


    }

}
