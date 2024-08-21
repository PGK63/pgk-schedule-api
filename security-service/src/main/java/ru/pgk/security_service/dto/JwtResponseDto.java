package ru.pgk.security_service.dto;

import ru.pgk.security_service.entities.AdminTypeEntity;
import ru.pgk.security_service.entities.UserEntity;

public record JwtResponseDto(
   Integer userId,
   UserEntity.Role role,
   AdminTypeEntity.Type adminType,
   String accessToken,
   String refreshToken
) {}
