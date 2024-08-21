package ru.pgk.main_service.features.admin.services.type;

import ru.pgk.main_service.features.admin.entities.AdminTypeEntity;

public interface AdminTypeService {

    AdminTypeEntity getByType(AdminTypeEntity.Type type);
}
