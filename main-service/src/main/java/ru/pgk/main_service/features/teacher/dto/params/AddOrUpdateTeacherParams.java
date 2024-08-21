package ru.pgk.main_service.features.teacher.dto.params;

import java.util.List;

public record AddOrUpdateTeacherParams(
        String firstName,
        String lastName,
        String middleName,
        String cabinet,
        List<Short> departmentIds
) {}
