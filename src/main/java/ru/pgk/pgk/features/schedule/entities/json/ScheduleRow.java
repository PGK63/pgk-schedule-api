package ru.pgk.pgk.features.schedule.entities.json;

import java.io.Serializable;
import java.util.List;

public record ScheduleRow(
        String group_name,
        String shift,
        List<ScheduleColumn> columns
) implements Serializable {}
