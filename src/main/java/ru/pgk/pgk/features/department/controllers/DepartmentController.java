package ru.pgk.pgk.features.department.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.pgk.features.department.dto.DepartmentDto;
import ru.pgk.pgk.features.department.mappers.DepartmentMapper;
import ru.pgk.pgk.features.department.services.DepartmentService;

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
}
