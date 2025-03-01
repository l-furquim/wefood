package com.lucas.pedido_ms.domains.order.enums;

public enum OrderStatus {
    WAITING_PAYMENT(1),
    PREPARING(2),
    SHIPPING(3),
    RECEIVED(4);

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
