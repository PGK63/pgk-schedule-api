package ru.pgk.main_service.features.admin.dto.param;

import ru.pgk.main_service.features.admin.entities.AdminTypeEntity;

public record CreateAdminParams(
        String username,
        String password,
        AdminTypeEntity.Type type
) {}
