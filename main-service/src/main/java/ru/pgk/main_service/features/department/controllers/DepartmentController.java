package ru.pgk.main_service.features.department.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.main_service.features.department.dto.DepartmentDto;
import ru.pgk.main_service.features.department.mappers.DepartmentMapper;
import ru.pgk.main_service.features.department.services.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
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
            @RequestParam String name
    ) {
        return departmentMapper.toDto(departmentService.add(name));
    }

    @PutMapping("{id}")
    @SecurityRequirements(
            value = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "X-API-KEY")
            }
    )
    private DepartmentDto update(
            @PathVariable Short id,
            @RequestParam String name
    ) {
        return departmentMapper.toDto(departmentService.update(id, name));
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
            @PathVariable Short id
    ) {
        departmentService.deleteById(id);
    }
}
