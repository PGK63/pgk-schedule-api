package ru.pgk.pgk.features.apiToken.services;

import ru.pgk.pgk.features.apiToken.entities.ApiTokenEntity;

import java.util.List;
import java.util.UUID;

public interface ApiTokenService {

    List<ApiTokenEntity> getAll();

    ApiTokenEntity getByToken(UUID token);

    ApiTokenEntity add();

    ApiTokenEntity deleteById(Integer id);
}
