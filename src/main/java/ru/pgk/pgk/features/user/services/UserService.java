package ru.pgk.pgk.features.user.services;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserService {

    UserEntity getById(Integer id);

    UserEntity getByTelegramId(Long id);

    UserEntity getByAliceId(String id);

    Boolean existByTelegramId(Long id);

    Boolean existByAliceId(String id);

    void deleteByTelegramId(Long id);
    void deleteByAliceId(String id);
}
