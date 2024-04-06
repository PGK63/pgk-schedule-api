package ru.pgk.pgk.features.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;

import java.util.Optional;

public interface AdminTypeRepository extends JpaRepository<AdminTypeEntity, Integer> {

    Optional<AdminTypeEntity> findByType(AdminTypeEntity.Type type);
}
