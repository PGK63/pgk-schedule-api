package ru.pgk.pgk.features.teacher.service.cache;

import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

public interface TeacherCacheService {

    void clearCacheById(TeacherEntity teacher);
}
