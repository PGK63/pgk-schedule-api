package ru.pgk.pgk.features.student.services.cache;

import ru.pgk.pgk.features.student.entities.StudentEntity;

public interface StudentCacheService {

    void clearCacheById(StudentEntity student);
}
