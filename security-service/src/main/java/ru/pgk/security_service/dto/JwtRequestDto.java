package ru.pgk.security_service.dto;

public record JwtRequestDto(
   String username,
   String password
) {}
