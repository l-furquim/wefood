package com.lucas.payment_ms.domains.account.exceptions;

public class AccountAlredyExistsException extends RuntimeException {
    public AccountAlredyExistsException(String message) {
        super(message);
    }
}
