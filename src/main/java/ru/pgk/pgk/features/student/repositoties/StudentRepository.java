package ru.pgk.pgk.features.student.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.student.entities.StudentEntity;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    Optional<StudentEntity> findByUserTelegramId(Long telegramId);
    Optional<StudentEntity> findByUserAliceId(String aliceId);
}
