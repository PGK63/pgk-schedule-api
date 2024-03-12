package ru.pgk.pgk.features.teacher.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

    List<TeacherEntity> getAllByDepartmentId(Short depId);
    Optional<TeacherEntity> findByUserTelegramId(Long telegramId);
    Optional<TeacherEntity> findByUserAliceId(String aliceId);
}
