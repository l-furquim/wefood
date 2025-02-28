package com.lucas.pedido_ms.domains.order.exception;

public class InvalidOrderCreationException extends RuntimeException {
    public InvalidOrderCreationException(String message){
        super(message);
    }
}
