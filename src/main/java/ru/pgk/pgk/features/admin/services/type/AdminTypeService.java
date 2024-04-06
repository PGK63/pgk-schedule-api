package ru.pgk.pgk.features.admin.services.type;

import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;

public interface AdminTypeService {

    AdminTypeEntity getByType(AdminTypeEntity.Type type);
}
