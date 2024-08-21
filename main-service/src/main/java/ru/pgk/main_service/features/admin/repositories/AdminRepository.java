package ru.pgk.main_service.features.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.main_service.features.admin.entities.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {}
