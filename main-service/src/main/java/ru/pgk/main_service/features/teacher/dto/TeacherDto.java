package ru.pgk.main_service.features.teacher.dto;

public record TeacherDto(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String cabinet
) {}
