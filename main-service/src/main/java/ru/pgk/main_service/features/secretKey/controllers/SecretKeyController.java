package ru.pgk.main_service.features.secretKey.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.main_service.features.secretKey.dto.SecretKeyDto;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;
import ru.pgk.main_service.features.secretKey.mappers.SecretKeyMapper;
import ru.pgk.main_service.features.secretKey.services.SecretKeyService;

@RestController
@RequiredArgsConstructor
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
