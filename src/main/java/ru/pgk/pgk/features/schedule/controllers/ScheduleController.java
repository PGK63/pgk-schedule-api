package ru.pgk.pgk.features.schedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.common.dto.PageDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleDto;
import ru.pgk.pgk.features.schedule.dto.params.GetScheduleType;
import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.mappers.ScheduleMapper;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.features.schedule.service.search.ScheduleSearchService;

import java.util.List;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleSearchService scheduleSearchService;

    private final ScheduleMapper scheduleMapper;

    @GetMapping
    private PageDto<ScheduleDto> getAll(
            @RequestParam(required = false) List<Short> departmentIds,
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

    @GetMapping("{id}/student/by-group-name/{name}")
    private ScheduleStudentResponse getByStudentGroupName(
            @PathVariable Integer id,
            @PathVariable String name
    ) {
        return scheduleService.studentGetByGroupName(id, name);
    }

    @GetMapping("{id}/student/by-telegram-id/{t-id}")
    private ScheduleStudentResponse getByStudentTelegramId(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        return scheduleService.studentGetByTelegramId(id, telegramId);
    }

    @GetMapping("student/by-alice-id/{a-id}")
    private ScheduleStudentResponse getByStudentAliceId(
            @PathVariable("a-id") String aliceId,
            @RequestParam GetScheduleType type
    ) {
        return scheduleService.studentGetByAliceId(type, aliceId);
    }

    @GetMapping("{id}/teacher/by-telegram-id/{t-id}")
    private ScheduleTeacherResponse getByTeacherTelegramId(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        return scheduleService.teacherGetByTelegramId(id, telegramId);
    }

    @GetMapping("teacher/by-alice-id/{a-id}")
    private ScheduleTeacherResponse getByTeacherAliceId(
            @PathVariable("a-id") String aliceId,
            @RequestParam GetScheduleType type
    ) {
        return scheduleService.teacherGetByAliceId(type, aliceId);
    }

    @GetMapping("{id}/teacher/{teacher-id}")
    private ScheduleTeacherResponse getByTeacherId(
            @PathVariable Integer id,
            @PathVariable(name = "teacher-id") Integer teacherId
    ) {
       return scheduleService.teacherGetByTeacherId(id, teacherId);
    }
}
