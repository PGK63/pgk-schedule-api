package ru.pgk.pgk.features.department.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Short> {}
