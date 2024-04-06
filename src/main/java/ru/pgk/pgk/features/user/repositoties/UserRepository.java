package ru.pgk.pgk.features.user.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM users u WHERE u.telegram.telegramId = ?1")
    Optional<UserEntity> findByTelegramId(Long telegramId);

    @Query("SELECT u FROM users u WHERE u.alice.aliceId = ?1")
    Optional<UserEntity> findByAliceId(String aliceId);

    @Query("SELECT u FROM users u WHERE u.usernamePassword.username = ?1")
    Optional<UserEntity> findByUsername(String username);

    @Query("DELETE FROM users u WHERE u.telegram.telegramId = ?1")
    void deleteByTelegramId(Long id);

    @Query("DELETE FROM users u WHERE u.alice.aliceId = ?1")
    void deleteByAliceId(String id);
}
