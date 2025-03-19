package com.lucas.pedido_ms.domains.order.exception;

public class SendingOrderMailException extends RuntimeException {
    public SendingOrderMailException(String message) {
        super(message);
    }
}
