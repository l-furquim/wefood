package com.lucas.profile_ms.domains.profile.dto;

public record CreateProfileDto(
        String email,
        String username,
        String password
) {
}
