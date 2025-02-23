package com.lucas.pedido_ms.domains.orderitem.exception;

public class InvalidOrderItemDataException extends RuntimeException {
    public InvalidOrderItemDataException(String message) {
        super(message);
    }
}
