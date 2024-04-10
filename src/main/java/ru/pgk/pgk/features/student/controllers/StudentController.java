package ru.pgk.pgk.features.student.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.student.dto.StudentDetailsDto;
import ru.pgk.pgk.features.student.dto.StudentDto;
import ru.pgk.pgk.features.student.dto.params.AddStudentParams;
import ru.pgk.pgk.features.student.mapper.StudentDetailsMapper;
import ru.pgk.pgk.features.student.mapper.StudentMapper;
import ru.pgk.pgk.features.student.services.StudentService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequestMapping("students")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final StudentMapper studentMapper;
    private final StudentDetailsMapper studentDetailsMapper;

    @GetMapping("/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private StudentDetailsDto getByTelegramId(
            @PathVariable Long id
    ) {
        return studentDetailsMapper.toDto(studentService.getByTelegramId(id));
    }

    @GetMapping("/by-alice-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private StudentDetailsDto getByAliceId(
            @PathVariable String id
    ) {
        return studentDetailsMapper.toDto(studentService.getByAliceId(id));
    }

    @PostMapping("/alice/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private StudentDto add(
            @PathVariable String id,
            @RequestBody AddStudentParams params
    ) {
        return studentMapper.toDto(studentService.add(id, params));
    }

    @PostMapping("/telegram/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private StudentDto add(
            @PathVariable Long id,
            @RequestBody AddStudentParams params
    ) {
        return studentMapper.toDto(studentService.add(id, params));
    }
}
