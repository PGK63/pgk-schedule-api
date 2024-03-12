package ru.pgk.pgk.features.student.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.student.entities.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    List<StudentEntity> findAllByDepartmentId(Short depId);
    Optional<StudentEntity> findByUserTelegramId(Long telegramId);
    Optional<StudentEntity> findByUserAliceId(String aliceId);
}
