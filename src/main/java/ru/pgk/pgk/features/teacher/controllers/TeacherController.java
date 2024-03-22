package ru.pgk.pgk.features.teacher.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.teacher.dto.TeacherDetailsDto;
import ru.pgk.pgk.features.teacher.dto.TeacherDto;
import ru.pgk.pgk.features.teacher.dto.params.AddTeacherParams;
import ru.pgk.pgk.features.teacher.mappers.TeacherDetailsMapper;
import ru.pgk.pgk.features.teacher.mappers.TeacherMapper;
import ru.pgk.pgk.features.teacher.repositoties.TeacherRepository;
import ru.pgk.pgk.features.teacher.service.TeacherService;
import ru.pgk.pgk.security.GlobalSecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("teachers")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private final TeacherMapper teacherMapper;
    private final TeacherDetailsMapper teacherDetailsMapper;

    private final TeacherRepository teacherRepository;

    @GetMapping
    private List<TeacherDto> getAll() {
        return teacherMapper.toDto(teacherRepository.findAll());
    }

    @GetMapping("/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private TeacherDetailsDto getByTelegramId(
            @PathVariable Long id
    ) {
        return teacherDetailsMapper.toDto(teacherService.getByTelegramId(id));
    }

    @GetMapping("/by-alice-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private TeacherDetailsDto getByAlicaId(
            @PathVariable String id
    ) {
        return teacherDetailsMapper.toDto(teacherService.getByAlicaId(id));
    }

    @PostMapping("/telegram/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private TeacherDto add(
            @PathVariable Long id,
            @RequestBody AddTeacherParams params
    ) {
        return teacherMapper.toDto(teacherService.add(id, params));
    }

    @PostMapping("/alice/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private TeacherDto add(
            @PathVariable String id,
            @RequestBody AddTeacherParams params
    ) {
        return teacherMapper.toDto(teacherService.add(id, params));
    }

    @PatchMapping("/by-telegram-id/{id}/first-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateFirstName(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        teacherService.updateFirstName(id, name);
    }

    @PatchMapping("/by-alice-id/{id}/first-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateFirstName(
            @PathVariable String id,
            @RequestParam String name
    ) {
        teacherService.updateFirstName(id, name);
    }

    @PatchMapping("/by-telegram-id/{id}/last-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateLastName(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        teacherService.updateLastName(id, name);
    }

    @PatchMapping("/by-alice-id/{id}/last-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateLastName(
            @PathVariable String id,
            @RequestParam String name
    ) {
        teacherService.updateLastName(id, name);
    }

    @PatchMapping("/by-telegram-id/{id}/cabinet")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateCabinet(
            @PathVariable Long id,
            @RequestParam String cabinet
    ) {
        teacherService.updateCabinet(id, cabinet);
    }

    @PatchMapping("/by-alice-id/{id}/cabinet")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateCabinet(
            @PathVariable String id,
            @RequestParam String cabinet
    ) {
        teacherService.updateCabinet(id, cabinet);
    }
}
