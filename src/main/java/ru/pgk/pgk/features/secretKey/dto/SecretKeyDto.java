package ru.pgk.pgk.features.secretKey.dto;

import ru.pgk.pgk.features.secretKey.entities.SecretKeyTypeEntity;

public record SecretKeyDto(
        SecretKeyTypeEntity.Type type,
        String key
) {}
