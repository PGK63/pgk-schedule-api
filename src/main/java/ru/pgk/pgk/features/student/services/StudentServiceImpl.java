package ru.pgk.pgk.features.student.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.student.dto.params.AddStudentParams;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.student.repositoties.StudentRepository;
import ru.pgk.pgk.features.user.entities.UserEntity;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final DepartmentService departmentService;

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public StudentEntity getById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentEntity getByTelegramId(Long id) {
        return studentRepository.findByUserTelegramId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentEntity getByAliceId(String id) {
        return studentRepository.findByUserAliceId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    @Transactional
    public StudentEntity add(Long telegramId, AddStudentParams params) {
        UserEntity user = new UserEntity();
        user.setTelegramId(telegramId);
        return add(user, params);
    }

    @Override
    @Transactional
    public StudentEntity add(String aliceId, AddStudentParams params) {
        UserEntity user = new UserEntity();
        user.setAliceId(aliceId);
        return add(user, params);
    }

    private StudentEntity add(UserEntity user, AddStudentParams params) {
        StudentEntity student = new StudentEntity();
        DepartmentEntity department = departmentService.getById(params.departmentId());
        student.setUser(user);
        student.setGroupName(params.groupName());
        student.setDepartment(department);
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void updateGroupName(Integer id, String groupName) {
        StudentEntity student = getById(id);
        updateGroupName(student, groupName);
    }

    @Override
    @Transactional
    public void updateGroupName(Long telegramId, String groupName) {
        StudentEntity student = getByTelegramId(telegramId);
        updateGroupName(student, groupName);
    }

    @Override
    @Transactional
    public void updateGroupName(String aliceId, String groupName) {
        StudentEntity student = getByAliceId(aliceId);
        updateGroupName(student, groupName);
    }

    private void updateGroupName(StudentEntity student, String groupName) {
        student.setGroupName(groupName);
        studentRepository.save(student);
    }
}
