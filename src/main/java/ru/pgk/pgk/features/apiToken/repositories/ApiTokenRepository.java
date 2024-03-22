package ru.pgk.pgk.features.apiToken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.apiToken.entities.ApiTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Integer> {

    Optional<ApiTokenEntity> findByToken(UUID token);
}
