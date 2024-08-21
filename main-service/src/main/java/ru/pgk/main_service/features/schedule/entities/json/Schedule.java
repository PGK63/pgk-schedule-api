package ru.pgk.main_service.features.schedule.entities.json;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record Schedule(
        LocalDate date,
        List<ScheduleRow> rows
) implements Serializable {}
