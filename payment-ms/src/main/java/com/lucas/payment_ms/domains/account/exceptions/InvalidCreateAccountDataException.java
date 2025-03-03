package com.lucas.payment_ms.domains.account.exceptions;

public class InvalidCreateAccountDataException extends RuntimeException {
    public InvalidCreateAccountDataException(String message) {
        super(message);
    }
}
