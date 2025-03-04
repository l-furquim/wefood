package com.lucas.notification_ms.domains.notification.enums;

public enum NotificationType {
    ORDER(1),
    PAYMENT(2);

    private int value;

    NotificationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
