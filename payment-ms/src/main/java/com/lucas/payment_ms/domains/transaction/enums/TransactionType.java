package com.lucas.payment_ms.domains.transaction.enums;

public enum TransactionType {

    ORDER_PAYMENT(1),
    REFUND(2);

    private int value;

    TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
