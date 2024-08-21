package ru.pgk.main_service.features.secretKey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;

import java.util.Optional;

public interface SecretKeyTypeRepository extends JpaRepository<SecretKeyTypeEntity, Short> {

    Optional<SecretKeyTypeEntity> findByValue(SecretKeyTypeEntity.Type type);
}
