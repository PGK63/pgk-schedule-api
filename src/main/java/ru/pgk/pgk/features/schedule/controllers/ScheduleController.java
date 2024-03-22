package ru.pgk.pgk.features.schedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.common.dto.PageDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleDto;
import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.mappers.ScheduleMapper;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.security.GlobalSecurityRequirement;

@RestController
@RequestMapping("schedules")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final ScheduleMapper scheduleMapper;

    @GetMapping
    private PageDto<ScheduleDto> getAll(
            @RequestParam Short departmentId,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        Page<ScheduleDto> page = scheduleService.getAll(departmentId, offset).map(scheduleMapper::toDto);
        return PageDto.fromPage(page);
    }

    @GetMapping("{id}/student/by-telegram-id/{t-id}")
    private ScheduleStudentResponse getByStudentTelegramId(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        return scheduleService.studentGetById(id, telegramId);
    }

    @GetMapping("{id}/teacher/by-telegram-id/{t-id}")
    private ScheduleTeacherResponse getByTeacherTelegramId(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        return scheduleService.teacherGetById(id, telegramId);
    }
}
