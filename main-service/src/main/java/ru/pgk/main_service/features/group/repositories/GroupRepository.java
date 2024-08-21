package ru.pgk.main_service.features.group.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.main_service.features.group.entities.GroupEntity;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    @Query("SELECT g FROM groups g WHERE LOWER(g.name) LIKE %?1%")
    Page<GroupEntity> search(String name, Pageable pageable);

    Optional<GroupEntity> findByNameAndDepartmentId(String name, Short departmentId);
}
