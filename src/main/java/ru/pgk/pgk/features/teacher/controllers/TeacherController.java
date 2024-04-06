package ru.pgk.pgk.features.teacher.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequestMapping("teachers")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class TeacherController { }
