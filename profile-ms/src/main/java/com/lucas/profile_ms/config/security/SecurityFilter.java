package com.lucas.profile_ms.config.security;

import com.lucas.profile_ms.repositories.ProfileRepository;
import com.lucas.profile_ms.services.IProfileService;
import com.lucas.profile_ms.services.ITokenService;
import com.lucas.profile_ms.services.impl.ProfileServiceImpl;
import com.lucas.profile_ms.services.impl.TokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);

        if(token != null){
            var email = tokenService.validateToken(token);

            UserDetails profile = profileRepository.findByEmailDetails(email);

            var authenticationProfile = new UsernamePasswordAuthenticationToken(
                    profile,
                    null,
                    profile.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationProfile);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
