package ru.pgk.security_service.services;

import ru.pgk.security_service.entities.UserEntity;

public interface UserQueriesService {
    UserEntity getByUsername(String username);
}
