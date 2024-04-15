package ru.pgk.pgk.features.secretKey.services.type;

import ru.pgk.pgk.features.secretKey.entities.SecretKeyTypeEntity;

public interface SecretKeyTypeService {

    SecretKeyTypeEntity getByType(SecretKeyTypeEntity.Type type);
}
