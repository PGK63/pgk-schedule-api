package ru.pgk.pgk.features.teacher.dto;

import ru.pgk.pgk.features.department.dto.DepartmentDto;

public record TeacherDetailsDto(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String cabinet,
        DepartmentDto department
) {}
