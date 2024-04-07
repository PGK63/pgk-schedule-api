package ru.pgk.pgk.features.apiToken.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.common.exceptions.ForbiddenException;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;
import ru.pgk.pgk.features.apiToken.dto.ApiTokenDto;
import ru.pgk.pgk.features.apiToken.mappers.ApiTokenMapper;
import ru.pgk.pgk.features.apiToken.services.ApiTokenService;
import ru.pgk.pgk.security.jwt.JwtEntity;

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
    private List<ApiTokenDto> getAll(
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(jwtEntity == null || jwtEntity.getAdminType() != AdminTypeEntity.Type.FULL)
            throw new ForbiddenException();

        return apiTokenMapper.toDto(apiTokenService.getAll());
    }

    @PostMapping
    private ApiTokenDto add(
            @RequestParam String name,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(jwtEntity == null || jwtEntity.getAdminType() != AdminTypeEntity.Type.FULL)
            throw new ForbiddenException();

        return apiTokenMapper.toDto(apiTokenService.add(name));
    }

    @DeleteMapping("{id}")
    private void deleteById(
            @PathVariable Integer id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(jwtEntity == null || jwtEntity.getAdminType() != AdminTypeEntity.Type.FULL)
            throw new ForbiddenException();

        apiTokenService.deleteById(id);
    }
}
