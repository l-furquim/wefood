package com.lucas.notification_ms.domains.notification.exceptions;

public class ErrorSendingNotification extends RuntimeException {
    public ErrorSendingNotification(String message) {
        super(message);
    }
}
