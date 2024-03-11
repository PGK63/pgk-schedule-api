package ru.pgk.pgk.features.schedule.dto.teacher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ScheduleTeacherResponse(
   LocalDate date,
   List<ScheduleTeacherColumnDto> columns
) implements Serializable {}
