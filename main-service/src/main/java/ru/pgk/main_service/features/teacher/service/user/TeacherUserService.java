package ru.pgk.main_service.features.teacher.service.user;

import ru.pgk.main_service.features.teacher.entities.TeacherUserEntity;

import java.util.List;

public interface TeacherUserService {

    List<TeacherUserEntity> getAllByTelegramNotNull(Short departmentId);

    TeacherUserEntity getByTelegramId(Long telegramId);
    TeacherUserEntity getByAliceId(String aliceId);

    TeacherUserEntity add(Integer teacherId, Long telegramId);
}
