package ru.pgk.main_service.features.secretKey.dto;

import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;

public record SecretKeyDto(
        SecretKeyTypeEntity.Type type,
        String key
) {}
