package com.lucas.mailsender_ms.domains.mail.exceptions;

public class MailNotFoundException extends RuntimeException {
    public MailNotFoundException(String message) {
        super(message);
    }
}
