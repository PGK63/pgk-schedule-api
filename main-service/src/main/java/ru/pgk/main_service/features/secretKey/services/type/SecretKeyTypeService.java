package ru.pgk.main_service.features.secretKey.services.type;

import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;

public interface SecretKeyTypeService {

    SecretKeyTypeEntity getByType(SecretKeyTypeEntity.Type type);
}
