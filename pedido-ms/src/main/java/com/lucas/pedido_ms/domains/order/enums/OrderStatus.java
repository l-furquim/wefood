package com.lucas.pedido_ms.domains.order.enums;

public enum OrderStatus {
    PREPARING(1),
    SHIPPING(2),
    RECEIVED(3);

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
