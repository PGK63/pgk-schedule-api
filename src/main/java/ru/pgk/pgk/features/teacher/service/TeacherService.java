package ru.pgk.pgk.features.teacher.service;

import ru.pgk.pgk.features.teacher.dto.params.AddTeacherParams;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

import java.util.List;

public interface TeacherService {

    List<TeacherEntity> getAll(Short departmentId);

    TeacherEntity getById(Integer id);
    TeacherEntity getByTelegramId(Long id);
    TeacherEntity getByAlicaId(String id);

    TeacherEntity add(Long telegramId, AddTeacherParams params);
    TeacherEntity add(String aliceId, AddTeacherParams params);

    void updateFirstName(Integer id, String name);
    void updateFirstName(Long telegramId, String name);
    void updateFirstName(String alicaId, String name);

    void updateLastName(Integer id, String name);
    void updateLastName(Long telegramId, String name);
    void updateLastName(String alicaId, String name);


    void updateCabinet(Integer id, String cabinet);
    void updateCabinet(Long telegramId, String cabinet);
    void updateCabinet(String alicaId, String cabinet);
}
