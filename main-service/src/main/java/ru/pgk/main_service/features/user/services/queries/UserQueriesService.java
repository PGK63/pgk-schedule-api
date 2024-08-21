package ru.pgk.main_service.features.user.services.queries;

import ru.pgk.main_service.features.user.entities.UserEntity;

public interface UserQueriesService {

    UserEntity getById(Integer id);

    UserEntity getByTelegramId(Long id);

    UserEntity getByAliceId(String id);
}
