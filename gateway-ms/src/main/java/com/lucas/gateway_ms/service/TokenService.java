package com.lucas.gateway_ms.service;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class TokenService {

    @Value("${api.security.token.secret}")
    private String SECRET;

    public void isTokenValid(String jwt) throws Exception {
        try {
            String assuredFormatedToken = extractToken(jwt);

            SignedJWT parsedToken = SignedJWT.parse(assuredFormatedToken);

            JWSVerifier verifier = new MACVerifier(SECRET.getBytes(StandardCharsets.UTF_8));

            if(!parsedToken.verify(verifier)) {
                log.error("Invalid jwt token");

                throw new Exception("Expired token");
            }

            Claims claims = Jwts
                    .parser()
                    .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();

            String user = claims.getSubject();

            // implementar session aqui.
        } catch(ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            throw new Exception("Token expired: {} ", e);
        } catch (Exception e) {
            log.error("Invalid token {}", e.getMessage());
            throw new Exception("Invalid token {}", e);
        }
    }

    public String getIdentifierFromToken(String jwt) {
        jwt = extractToken(jwt);

        Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }


    private String extractToken(String authToken) {
        if (authToken.toLowerCase().startsWith("bearer")) {
            return authToken.substring("bearer ".length());
        }
        return authToken;
    }
}
