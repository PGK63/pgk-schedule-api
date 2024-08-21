package ru.pgk.main_service.features.schedule.dto;

import ru.pgk.main_service.features.department.dto.DepartmentDto;

import java.time.LocalDate;

public record ScheduleDto(
        Integer id,
        LocalDate date,
        DepartmentDto department
) {}
