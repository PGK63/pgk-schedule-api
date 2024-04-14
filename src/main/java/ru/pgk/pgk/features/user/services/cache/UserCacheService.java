package ru.pgk.pgk.features.user.services.cache;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserCacheService {

    void clearAll(UserEntity user);
}
