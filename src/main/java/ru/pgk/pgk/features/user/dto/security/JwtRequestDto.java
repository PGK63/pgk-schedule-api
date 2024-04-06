package ru.pgk.pgk.features.user.dto.security;

public record JwtRequestDto(
        String username,
        String password
) {}
