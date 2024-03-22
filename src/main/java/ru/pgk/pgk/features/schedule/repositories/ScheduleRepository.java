package ru.pgk.pgk.features.schedule.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    Page<ScheduleEntity> findAllByDepartmentIdOrderByDateDesc(Short id, Pageable pageable);

    Optional<ScheduleEntity> findByDepartmentIdAndDate(Short departmentId, LocalDate date);
}
