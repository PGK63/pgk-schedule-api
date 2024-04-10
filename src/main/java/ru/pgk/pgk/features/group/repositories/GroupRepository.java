package ru.pgk.pgk.features.group.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.group.entities.GroupEntity;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    Optional<GroupEntity> findByNameAndDepartmentId(String name, Short departmentId);
}
