package ru.pgk.pgk.features.user.dto.security;

import lombok.Data;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

@Data
public class JwtResponseDto {

    private Integer userId;
    private UserEntity.Role role;
    private AdminTypeEntity.Type adminType;
    private String accessToken;
    private String refreshToken;
}
