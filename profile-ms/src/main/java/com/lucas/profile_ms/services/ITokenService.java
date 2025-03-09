package com.lucas.profile_ms.services;

import com.lucas.profile_ms.domains.profile.Profile;

import java.time.Instant;

public interface ITokenService {

    String generateToken(Profile profile);
    String validateToken(String token);
    Instant getExpirationDate();

}
