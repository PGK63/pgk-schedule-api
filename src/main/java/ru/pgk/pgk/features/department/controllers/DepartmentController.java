package ru.pgk.pgk.features.department.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.common.exceptions.ForbiddenException;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;
import ru.pgk.pgk.features.department.dto.DepartmentDto;
import ru.pgk.pgk.features.department.mappers.DepartmentMapper;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;
import ru.pgk.pgk.security.jwt.JwtEntity;

import java.util.List;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
@GlobalSecurityRequirement
public class DepartmentController {

    private final DepartmentService departmentService;

    private final DepartmentMapper departmentMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<DepartmentDto> getAll() {
        return departmentMapper.toDto(departmentService.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirements(
            value = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "X-API-KEY")
            }
    )
    private DepartmentDto add(
            @RequestParam String name,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(jwtEntity == null || jwtEntity.getAdminType() == null)
            throw new ForbiddenException();

        return departmentMapper.toDto(departmentService.add(name));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirements(
            value = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "X-API-KEY")
            }
    )
    private void deleteById(
            @PathVariable Short id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(jwtEntity == null || jwtEntity.getAdminType() == null)
            throw new ForbiddenException();

        departmentService.deleteById(id);
    }
}
