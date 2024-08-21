package ru.pgk.main_service.features.student.services;

import ru.pgk.main_service.features.student.dto.params.AddStudentParams;
import ru.pgk.main_service.features.student.entities.StudentEntity;

import java.util.List;

public interface StudentService {

    List<StudentEntity> getAllByTelegramNotNull(Short departmentId);

    StudentEntity getById(Integer id);
    StudentEntity getByTelegramId(Long id);
    StudentEntity getByAliceId(String id);

    StudentEntity add(Long telegramId, AddStudentParams params);
    StudentEntity add(String aliceId, AddStudentParams params);
}
