package ru.pgk.main_service.features.teacher.service.queries;

import org.springframework.data.domain.Page;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;

public interface TeacherQueriesService {

    Page<TeacherEntity> getAll(String name, Integer offset);

    TeacherEntity getById(Integer id);

    TeacherEntity getByCabinet(String cabinet);
}
