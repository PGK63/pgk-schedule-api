package ru.pgk.main_service.features.schedule.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    @Query("SELECT s FROM schedules s WHERE s.department.id IN :departmentIds ORDER BY s.date DESC")
    Page<ScheduleEntity> findAllByDepartmentIdsOrderByDateDesc(List<Short> departmentIds, Pageable pageable);

    @Query("SELECT s FROM schedules s ORDER BY s.date DESC")
    Page<ScheduleEntity> findAllDateDesc(Pageable pageable);

    Optional<ScheduleEntity> findByDepartmentIdAndDate(Short departmentId, LocalDate date);

    @Query("SELECT s FROM schedules s WHERE s.department.id IN :departmentIds AND s.date > :date ORDER BY s.date DESC")
    Optional<ScheduleEntity> findByNextDateAndDepartmentIds(LocalDate date, List<Short> departmentIds);

    @Query("SELECT s FROM schedules s WHERE s.department.id IN :departmentIds AND s.date = :date")
    Optional<ScheduleEntity> findByDateAndDepartmentIds(LocalDate date, List<Short> departmentIds);

    @Query(
            "SELECT s FROM schedules s WHERE s.department.id " +
                    "IN :departmentIds AND s.date IN(SELECT max(s.date) FROM schedules s)"
    )
    Optional<ScheduleEntity> findByLastDate(List<Short> departmentIds);
}
