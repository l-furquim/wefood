package com.lucas.pedido_ms.domains.order.exception;

public class InvalidOrderUpdateException extends RuntimeException {
    public InvalidOrderUpdateException(String message) {
        super(message);
    }
}
