package ru.pgk.pgk.features.user.services;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserService {
    void deleteByTelegramId(Long id);
    void deleteByAliceId(String id);
}
