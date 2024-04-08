package ru.pgk.pgk.features.teacher.service;

import org.springframework.data.domain.Page;
import ru.pgk.pgk.features.teacher.dto.params.AddOrUpdateTeacherParams;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

public interface TeacherService {

    Page<TeacherEntity> getAll(String name, Integer offset);

    TeacherEntity getById(Integer id);

    TeacherEntity add(AddOrUpdateTeacherParams params);

    TeacherEntity update(Integer id, AddOrUpdateTeacherParams params);

    void deleteById(Integer id);
}
