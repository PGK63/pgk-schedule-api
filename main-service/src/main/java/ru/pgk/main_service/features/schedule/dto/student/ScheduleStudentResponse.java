package ru.pgk.main_service.features.schedule.dto.student;

import ru.pgk.main_service.features.schedule.entities.json.ScheduleColumn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ScheduleStudentResponse(
        LocalDate date,
        String shift,
        List<ScheduleColumn> columns
) implements Serializable {}
