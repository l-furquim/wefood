package com.lucas.profile_ms.domains.profile.dto;

public record ConfirmCodeDto(
        String email,
        String code
) {
}
