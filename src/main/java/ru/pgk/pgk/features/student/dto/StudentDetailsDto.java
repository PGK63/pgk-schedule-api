package ru.pgk.pgk.features.student.dto;

import ru.pgk.pgk.features.department.dto.DepartmentDto;

public record StudentDetailsDto(
        Integer id,
        String groupName,
        DepartmentDto department
) {}
