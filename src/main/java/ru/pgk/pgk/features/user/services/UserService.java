package ru.pgk.pgk.features.user.services;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserService {

    UserEntity getById(Integer id);

    UserEntity getByUsername(String username);

    UserEntity getByTelegramId(Long id);

    UserEntity getByAliceId(String id);

    void deleteByTelegramId(Long id);
    void deleteByAliceId(String id);
}
