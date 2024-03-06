package ru.pgk.pgk.features.student.services;

import ru.pgk.pgk.features.student.dto.params.AddStudentParams;
import ru.pgk.pgk.features.student.entities.StudentEntity;

public interface StudentService {

    StudentEntity getById(Integer id);
    StudentEntity getByTelegramId(Long id);
    StudentEntity getByAliceId(String id);

    StudentEntity add(Long telegramId, AddStudentParams params);
    StudentEntity add(String aliceId, AddStudentParams params);

    void updateGroupName(Integer id, String groupName);
    void updateGroupName(Long telegramId, String groupName);
    void updateGroupName(String aliceId, String groupName);
}
