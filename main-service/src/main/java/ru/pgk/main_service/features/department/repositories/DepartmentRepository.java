package ru.pgk.main_service.features.department.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Short> {}
