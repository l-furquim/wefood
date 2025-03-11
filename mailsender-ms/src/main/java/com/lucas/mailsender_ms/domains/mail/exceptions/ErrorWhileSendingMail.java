package com.lucas.mailsender_ms.domains.mail.exceptions;

public class ErrorWhileSendingMail extends RuntimeException {
    public ErrorWhileSendingMail(String message) {
        super(message);
    }
}
