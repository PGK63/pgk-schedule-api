package ru.pgk.pgk.features.secretKey.services.queries;

import ru.pgk.pgk.features.secretKey.entities.SecretKeyEntity;

public interface SecretKeyQueriesService {
    SecretKeyEntity getByKey(String key);
}
