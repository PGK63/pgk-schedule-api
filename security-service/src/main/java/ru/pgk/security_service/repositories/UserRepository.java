package ru.pgk.security_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.security_service.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM users u WHERE u.usernamePassword.username = :username")
    Optional<UserEntity> findByUsername(String username);
}
