package ru.pgk.main_service.features.teacher.service.queries;

import ru.pgk.main_service.features.teacher.entities.TeacherEntity;

public interface TeacherQueriesService {
    TeacherEntity getById(Integer id);
}
