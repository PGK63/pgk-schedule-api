package ru.pgk.main_service.features.secretKey.services.queries;

import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;

public interface SecretKeyQueriesService {
    SecretKeyEntity getByKey(String key);
}
