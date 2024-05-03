package ru.pgk.pgk.features.user.dto;

import ru.pgk.pgk.features.student.dto.StudentDetailsDto;
import ru.pgk.pgk.features.teacher.dto.TeacherDetailsDto;
import ru.pgk.pgk.features.user.entities.UserEntity;

public record UserDetailsDto(
        Integer id,
        String aliceId,
        UserEntity.Role role,
        StudentDetailsDto student,
        TeacherDetailsDto teacher
) {}
