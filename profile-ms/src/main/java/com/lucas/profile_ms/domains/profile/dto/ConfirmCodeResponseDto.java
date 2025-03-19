package com.lucas.profile_ms.domains.profile.dto;

public record ConfirmCodeResponseDto(
        String username,
        String email,
        String password,
        String type
) {
}
