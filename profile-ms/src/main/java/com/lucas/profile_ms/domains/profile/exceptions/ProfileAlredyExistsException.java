package com.lucas.profile_ms.domains.profile.exceptions;

public class ProfileAlredyExistsException extends RuntimeException {
    public ProfileAlredyExistsException(String message) {
        super(message);
    }
}
