package ru.pgk.pgk.features.secretKey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyTypeEntity;

import java.util.Optional;

public interface SecretKeyTypeRepository extends JpaRepository<SecretKeyTypeEntity, Short> {

    Optional<SecretKeyTypeEntity> findByValue(SecretKeyTypeEntity.Type type);
}
