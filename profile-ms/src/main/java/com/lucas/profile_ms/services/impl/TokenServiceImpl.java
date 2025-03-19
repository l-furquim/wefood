package com.lucas.profile_ms.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.exceptions.InvalidJwtTokenException;
import com.lucas.profile_ms.domains.profile.exceptions.TokenGenerationException;
import com.lucas.profile_ms.services.ITokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements ITokenService {

    @Value("${api.security.token.secret}")
    private static final String SECRET = "tomei-agua-hoje";

    @Override
    public String generateToken(Profile profile) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    .withIssuer("wefood-profile-ms")
                    .withSubject(profile.getEmail())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new TokenGenerationException("Error while generating token " + exception.getMessage());
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.require(algorithm)
                    .withIssuer("wefood-profile-ms")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new InvalidJwtTokenException("Received a invalid jwt token " + exception.getMessage());
        }
    }

    @Override
    public Instant getExpirationDate() {
        return LocalDateTime.now().
                plusHours(2).
                toInstant(ZoneOffset.of("-03:00"));
    }
    @Override
    public Instant getExpirationDate(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.require(algorithm)
                .withIssuer("wefood-profile-ms")
                .build()
                .verify(token)
                .getExpiresAtAsInstant();
    }
}
