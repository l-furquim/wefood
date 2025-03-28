package com.lucas.pedido_ms.domains.orderitem.exception;

public class OrderItemRestaurantNotFound extends RuntimeException {
    public OrderItemRestaurantNotFound(String message) {
        super(message);
    }
}
