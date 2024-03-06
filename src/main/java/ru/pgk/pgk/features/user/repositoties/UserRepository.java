package ru.pgk.pgk.features.user.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByTelegramId(Long telegramId);

    Optional<UserEntity> findByAliceId(String aliceId);
}
