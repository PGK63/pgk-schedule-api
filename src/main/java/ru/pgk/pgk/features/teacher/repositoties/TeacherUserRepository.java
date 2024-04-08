package ru.pgk.pgk.features.teacher.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;

import java.util.List;
import java.util.Optional;

public interface TeacherUserRepository extends JpaRepository<TeacherUserEntity, Integer> {

    @Query("SELECT t FROM teacher_users t WHERE t.teacher.department.id = ?1 AND t.user.telegram != null")
    List<TeacherUserEntity> findAllByTelegramNotNull(Short departmentId);

    @Query("SELECT t FROM teacher_users t WHERE t.user.telegram.telegramId = ?1")
    Optional<TeacherUserEntity> findByTelegramId(Long telegramId);
}
