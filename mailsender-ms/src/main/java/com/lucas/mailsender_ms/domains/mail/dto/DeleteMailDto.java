package com.lucas.mailsender_ms.domains.mail.dto;

public record DeleteMailDto(
        Long mailId,
        String userId
) {
}
