package ru.pgk.pgk.features.schedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.pgk.features.schedule.dto.ScheduleRowDto;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherColumnDto;
import ru.pgk.pgk.features.schedule.service.ScheduleServiceImpl;

import java.util.List;

@RestController
@RequestMapping("schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;

    @GetMapping
    private ScheduleRowDto get() {
        return scheduleService.getByGroupName("ИСП-34");
    }

    @GetMapping("teacher/{telegram-id}")
    private List<ScheduleTeacherColumnDto> get(
            @PathVariable("telegram-id") Long id
    ) {
        return scheduleService.getByTeacherTelegramId(id);
    }
}
