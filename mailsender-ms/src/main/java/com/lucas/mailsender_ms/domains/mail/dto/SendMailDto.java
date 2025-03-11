package com.lucas.mailsender_ms.domains.mail.dto;

import com.lucas.mailsender_ms.domains.mail.enums.MailType;

public record SendMailDto(
        String to,
        String from,
        String subject,
        String content,
        String userId,
        MailType type
) {
}
