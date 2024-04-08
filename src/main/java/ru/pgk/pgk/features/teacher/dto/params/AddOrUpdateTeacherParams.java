package ru.pgk.pgk.features.teacher.dto.params;

public record AddOrUpdateTeacherParams(
        String firstName,
        String lastName,
        String middleName,
        String cabinet,
        Short departmentId
) {}
