package ru.pgk.pgk.features.schedule.entities.json;

import java.io.Serializable;

public record ScheduleColumn(
        Integer number,
        String teacher,
        String cabinet,
        Boolean exam
) implements Serializable {}
