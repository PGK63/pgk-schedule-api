package ru.pgk.pgk.features.teacher.dto;

public record TeacherDto(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String cabinet
) {}
