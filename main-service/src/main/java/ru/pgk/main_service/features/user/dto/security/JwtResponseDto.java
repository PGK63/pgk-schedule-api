package ru.pgk.main_service.features.user.dto.security;

import lombok.Data;
import ru.pgk.main_service.features.admin.entities.AdminTypeEntity;
import ru.pgk.main_service.features.user.entities.UserEntity;

@Data
public class JwtResponseDto {

    private Integer userId;
    private UserEntity.Role role;
    private AdminTypeEntity.Type adminType;
    private String accessToken;
    private String refreshToken;
}
