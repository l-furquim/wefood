package com.lucas.profile_ms.domains.profile.dto;

public record SendMailConfirmationDto(
        String to,
        String from,
        String subject,
        String content,
        String userId,
        String type
) {
}
