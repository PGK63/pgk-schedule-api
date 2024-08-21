package ru.pgk.main_service.features.user.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.main_service.features.user.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM users u WHERE u.telegram.telegramId = ?1")
    Optional<UserEntity> findByTelegramId(Long telegramId);

    @Query("SELECT u FROM users u JOIN FETCH u.teacher t JOIN FETCH t.teacher.departments WHERE u.telegram.telegramId = ?1")
    Optional<UserEntity> findByTelegramIdWithTeacherAndDepartments(Long id);

    @Query("SELECT u FROM users u WHERE u.alice.aliceId = ?1")
    Optional<UserEntity> findByAliceId(String aliceId);

    @Modifying
    @Query("DELETE FROM users u WHERE u.telegram.telegramId = ?1")
    void deleteByTelegramId(Long id);

    @Modifying
    @Query("DELETE FROM users u WHERE u.alice.aliceId = ?1")
    void deleteByAliceId(String id);
}
