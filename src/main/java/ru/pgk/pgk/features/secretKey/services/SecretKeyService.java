package ru.pgk.pgk.features.secretKey.services;

import ru.pgk.pgk.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyTypeEntity;

public interface SecretKeyService {

    SecretKeyEntity getByKey(String key);

    SecretKeyEntity getOrCreate(Long telegramId, SecretKeyTypeEntity.Type type);
}
