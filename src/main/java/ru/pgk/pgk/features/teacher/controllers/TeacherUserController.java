package ru.pgk.pgk.features.teacher.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;
import ru.pgk.pgk.features.teacher.service.user.TeacherUserService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequestMapping("teacher")
@RequiredArgsConstructor
@GlobalSecurityRequirement
public class TeacherUserController {

    private final TeacherUserService teacherUserService;

    @PostMapping("{id}/user/by-telegram-id/{t-id}")
    private Integer add(
            @PathVariable Integer id,
            @PathVariable("t-id") Long telegramId
    ) {
        TeacherUserEntity teacherUser = teacherUserService.add(id, telegramId);
        return teacherUser.getId();
    }
}
