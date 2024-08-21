package ru.pgk.pgk.features.apiToken.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.apiToken.dto.ApiTokenDto;
import ru.pgk.pgk.features.apiToken.mappers.ApiTokenMapper;
import ru.pgk.pgk.features.apiToken.services.ApiTokenService;

import java.util.List;

@RestController
@RequestMapping("/api-token")
@RequiredArgsConstructor
@SecurityRequirements(
        value = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "X-API-KEY")
        }
)
public class ApiTokenController {

    private final ApiTokenService apiTokenService;

    private final ApiTokenMapper apiTokenMapper;

    @GetMapping
    private List<ApiTokenDto> getAll() {
        return apiTokenMapper.toDto(apiTokenService.getAll());
    }

    @PostMapping
    private ApiTokenDto add(
            @RequestParam String name
    ) {
        return apiTokenMapper.toDto(apiTokenService.add(name));
    }

    @DeleteMapping("{id}")
    private void deleteById(
            @PathVariable Integer id
    ) {
        apiTokenService.deleteById(id);
    }
}
