package ru.pgk.pgk.features.department.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.department.dto.DepartmentDto;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.mappers.DepartmentMapper;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.security.GlobalSecurityRequirement;

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
    private Short add(
            @RequestParam String name
    ) {
        DepartmentEntity department = departmentService.add(name);
        return department.getId();
    }
}
