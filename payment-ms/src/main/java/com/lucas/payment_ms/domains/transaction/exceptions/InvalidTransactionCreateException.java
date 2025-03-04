package com.lucas.payment_ms.domains.transaction.exceptions;

public class InvalidTransactionCreateException extends RuntimeException {
    public InvalidTransactionCreateException(String message) {
        super(message);
    }
}
