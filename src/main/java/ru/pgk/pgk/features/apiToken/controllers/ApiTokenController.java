package ru.pgk.pgk.features.apiToken.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.apiToken.dto.ApiTokenDto;
import ru.pgk.pgk.features.apiToken.mappers.ApiTokenMapper;
import ru.pgk.pgk.features.apiToken.services.ApiTokenService;
import ru.pgk.pgk.security.GlobalSecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api-token")
@RequiredArgsConstructor
@GlobalSecurityRequirement
public class ApiTokenController {

    private final ApiTokenService apiTokenService;

    private final ApiTokenMapper apiTokenMapper;

    @GetMapping
    private List<ApiTokenDto> getAll() {
        return apiTokenMapper.toDto(apiTokenService.getAll());
    }

    @PostMapping
    private Integer add() {
        return apiTokenService.add().getId();
    }

    @DeleteMapping("{id}")
    private void deleteById(
            @PathVariable Integer id
    ) {
        apiTokenService.deleteById(id);
    }
}
