package ru.pgk.pgk.features.teacher.service;

import ru.pgk.pgk.features.teacher.dto.params.AddOrUpdateTeacherParams;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

public interface TeacherService {
    TeacherEntity add(AddOrUpdateTeacherParams params);

    TeacherEntity update(Integer id, AddOrUpdateTeacherParams params);

    void deleteById(Integer id);
}
