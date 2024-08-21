package ru.pgk.main_service.features.teacher.dto;

import ru.pgk.main_service.features.department.dto.DepartmentDto;

import java.util.List;

public record TeacherDetailsDto(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String cabinet,
        List<DepartmentDto> departments
) {}
