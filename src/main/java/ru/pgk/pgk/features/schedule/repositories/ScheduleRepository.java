package ru.pgk.pgk.features.schedule.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    @Query("SELECT s FROM schedules s WHERE s.department.id IN :departmentIds ORDER BY s.date DESC")
    Page<ScheduleEntity> findAllByDepartmentIdsOrderByDateDesc(List<Short> departmentIds, Pageable pageable);

    Optional<ScheduleEntity> findByDepartmentIdAndDate(Short departmentId, LocalDate date);
}
