package ru.pgk.main_service.features.teacher.service;

import ru.pgk.main_service.features.teacher.dto.params.AddOrUpdateTeacherParams;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;

public interface TeacherService {
    TeacherEntity add(AddOrUpdateTeacherParams params);

    TeacherEntity update(Integer id, AddOrUpdateTeacherParams params);

    TeacherEntity deleteById(Integer id);
}
