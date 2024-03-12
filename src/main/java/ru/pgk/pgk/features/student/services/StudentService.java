package ru.pgk.pgk.features.student.services;

import ru.pgk.pgk.features.student.dto.params.AddStudentParams;
import ru.pgk.pgk.features.student.entities.StudentEntity;

import java.util.List;

public interface StudentService {

    List<StudentEntity> getAll(Short departmentId);

    StudentEntity getById(Integer id);
    StudentEntity getByTelegramId(Long id);
    StudentEntity getByAliceId(String id);

    StudentEntity add(Long telegramId, AddStudentParams params);
    StudentEntity add(String aliceId, AddStudentParams params);

    void updateGroupName(Integer id, String groupName);
    StudentEntity updateGroupName(Long telegramId, String groupName);
    void updateGroupName(String aliceId, String groupName);
}
