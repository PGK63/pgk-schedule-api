package ru.pgk.main_service.features.student.dto;

import ru.pgk.main_service.features.department.dto.DepartmentDto;

public record StudentDetailsDto(
        Integer id,
        String groupName,
        DepartmentDto department
) {}
