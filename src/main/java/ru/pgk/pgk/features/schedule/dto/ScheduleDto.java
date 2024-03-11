package ru.pgk.pgk.features.schedule.dto;

import java.time.LocalDate;

public record ScheduleDto(
        Integer id,
        LocalDate date
) {}
