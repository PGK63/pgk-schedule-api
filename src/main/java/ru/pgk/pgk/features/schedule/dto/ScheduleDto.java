package ru.pgk.pgk.features.schedule.dto;

import ru.pgk.pgk.features.department.dto.DepartmentDto;

import java.time.LocalDate;

public record ScheduleDto(
        Integer id,
        LocalDate date,
        DepartmentDto department
) {}
