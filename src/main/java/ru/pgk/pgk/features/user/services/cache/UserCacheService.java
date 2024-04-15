package ru.pgk.pgk.features.user.services.cache;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserCacheService {
    UserEntity update(UserEntity user);
    void clearAll(UserEntity user);
}
