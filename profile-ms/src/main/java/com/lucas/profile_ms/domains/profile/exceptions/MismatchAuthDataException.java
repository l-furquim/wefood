package com.lucas.profile_ms.domains.profile.exceptions;

public class MismatchAuthDataException extends RuntimeException {
    public MismatchAuthDataException(String message) {
        super(message);
    }
}
