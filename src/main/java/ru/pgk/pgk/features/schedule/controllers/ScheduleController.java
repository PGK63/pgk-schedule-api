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
import ru.pgk.pgk.features.schedule.service.search.ScheduleSearchService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("schedules")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleSearchService scheduleSearchService;

    private final ScheduleMapper scheduleMapper;

    @GetMapping
    private PageDto<ScheduleDto> getAll(
            @RequestParam List<Short> departmentIds,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        Page<ScheduleDto> page = scheduleService.getAll(departmentIds, offset).map(scheduleMapper::toDto);
        return PageDto.fromPage(page);
    }

    @GetMapping("by-teacher-id/{id}")
    private PageDto<ScheduleDto> getAllByTeacherId(
            @PathVariable("id") Integer teacherId,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        Page<ScheduleDto> page = scheduleSearchService.getAllByTeacherId(teacherId, offset).map(scheduleMapper::toDto);
        return PageDto.fromPage(page);
    }

    @GetMapping("{id}/student/by-telegram-id/{t-id}")
    private ScheduleStudentResponse getByStudentTelegramId(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        return scheduleService.studentGetByTelegramId(id, telegramId);
    }

    @GetMapping("{id}/teacher/by-telegram-id/{t-id}")
    private ScheduleTeacherResponse getByTeacherTelegramId(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        return scheduleService.teacherGetByTelegramId(id, telegramId);
    }

    @GetMapping("{id}/teacher/{teacher-id}")
    private ScheduleTeacherResponse getByTeacherId(
            @PathVariable Integer id,
            @PathVariable(name = "teacher-id") Integer teacherId
    ) {
       return scheduleService.teacherGetByTeacherId(id, teacherId);
    }
}
