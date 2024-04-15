package ru.pgk.pgk.features.secretKey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyEntity;

import java.util.Optional;

public interface SecretKeyRepository extends JpaRepository<SecretKeyEntity, SecretKeyEntity.Id> {

    Optional<SecretKeyEntity> findByKey(String key);
}
