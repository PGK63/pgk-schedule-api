package ru.pgk.main_service.features.user.services.cache;

import ru.pgk.main_service.features.user.entities.UserEntity;

public interface UserCacheService {
    UserEntity update(UserEntity user);
    void clearAll(UserEntity user);
}
