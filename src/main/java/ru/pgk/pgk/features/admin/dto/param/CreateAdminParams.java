package ru.pgk.pgk.features.admin.dto.param;

import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;

public record CreateAdminParams(
        String username,
        String password,
        AdminTypeEntity.Type type
) {}
