package com.lucas.payment_ms.domains.account.enums;

public enum AccountType {

    COMUM(1),
    RESTAURANT(2);

    private int value;

    AccountType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
