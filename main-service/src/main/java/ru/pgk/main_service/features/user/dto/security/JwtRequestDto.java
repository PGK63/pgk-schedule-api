package ru.pgk.main_service.features.user.dto.security;

public record JwtRequestDto(
        String username,
        String password
) {}
