package ru.pgk.pgk.features.secretKey.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.secretKey.dto.SecretKeyDto;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyTypeEntity;
import ru.pgk.pgk.features.secretKey.mappers.SecretKeyMapper;
import ru.pgk.pgk.features.secretKey.services.SecretKeyService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequiredArgsConstructor
@GlobalSecurityRequirement
@RequestMapping("secret-keys")
public class SecretKeyController {

    private final SecretKeyService secretKeyService;

    private final SecretKeyMapper secretKeyMapper;

    @GetMapping("/by-telegram-id/{id}")
    private SecretKeyDto getOrCreate(
            @PathVariable Long id,
            @RequestParam SecretKeyTypeEntity.Type type
    ) {
        return secretKeyMapper.toDto(secretKeyService.getOrCreate(id, type));
    }
}
