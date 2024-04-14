package ru.pgk.pgk.features.user.services.queries;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserQueriesService {

    UserEntity getById(Integer id);

    UserEntity getByUsername(String username);

    UserEntity getByTelegramId(Long id);

    UserEntity getByAliceId(String id);
}
