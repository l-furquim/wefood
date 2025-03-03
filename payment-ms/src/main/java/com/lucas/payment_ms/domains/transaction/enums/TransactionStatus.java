package com.lucas.payment_ms.domains.transaction.enums;

public enum TransactionStatus {
    PENDING(1),
    ERROR(2),
    FRAUD(3),
    PROCESSED(4);

    private int value;

    TransactionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
