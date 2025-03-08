package com.lucas.profile_ms.domains.profile.exceptions;

public class ConfirmationCodeDoesntExists extends RuntimeException {
    public ConfirmationCodeDoesntExists(String message) {
        super(message);
    }
}
