package ru.pgk.main_service.features.secretKey.services;

import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;

public interface SecretKeyService {
    SecretKeyEntity getOrCreate(Long telegramId, SecretKeyTypeEntity.Type type);

    void deleteByKey(String key);
}
