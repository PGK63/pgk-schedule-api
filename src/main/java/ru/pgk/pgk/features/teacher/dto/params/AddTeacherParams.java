package ru.pgk.pgk.features.teacher.dto.params;

public record AddTeacherParams(
        String firstName,
        String lastName,
        String cabinet,
        Short departmentId
) {}
