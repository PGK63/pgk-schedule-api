package ru.pgk.pgk.features.student.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.pgk.features.student.entities.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    @Query("SELECT s FROM student_users s WHERE s.group.department.id = ?1 and s.user.telegram != null")
    List<StudentEntity> findAllByDepartmentIdAndTelegramNotNull(Short depId);

    @Query("SELECT s FROM student_users s WHERE s.user.telegram.telegramId = ?1")
    Optional<StudentEntity> findByUserTelegramId(Long telegramId);

    @Query("SELECT s FROM student_users s WHERE s.user.alice.aliceId = ?1")
    Optional<StudentEntity> findByUserAliceId(String aliceId);
}
