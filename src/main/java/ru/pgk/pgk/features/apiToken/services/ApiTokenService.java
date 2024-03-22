package ru.pgk.pgk.features.apiToken.services;

import ru.pgk.pgk.features.apiToken.entities.ApiTokenEntity;

import java.util.UUID;

public interface ApiTokenService {

    ApiTokenEntity getByToken(UUID token);
}
