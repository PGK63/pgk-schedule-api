package ru.pgk.main_service.features.schedule.dto.teacher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ScheduleTeacherResponse(
   LocalDate date,
   List<ScheduleTeacherRowDto> rows
) implements Serializable {}
