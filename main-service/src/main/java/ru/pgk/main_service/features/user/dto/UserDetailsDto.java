package ru.pgk.main_service.features.user.dto;

import ru.pgk.main_service.features.student.dto.StudentDetailsDto;
import ru.pgk.main_service.features.teacher.dto.TeacherDetailsDto;
import ru.pgk.main_service.features.user.entities.UserEntity;

public record UserDetailsDto(
        Integer id,
        String aliceId,
        UserEntity.Role role,
        StudentDetailsDto student,
        TeacherDetailsDto teacher
) {}
