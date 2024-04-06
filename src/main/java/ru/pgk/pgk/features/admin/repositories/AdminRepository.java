package ru.pgk.pgk.features.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.admin.entities.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {}
