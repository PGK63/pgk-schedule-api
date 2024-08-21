package ru.pgk.main_service.features.schedule.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.schedule.entities.json.ScheduleRow;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity(name = "schedules")
public class ScheduleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<ScheduleRow> rows;

    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentEntity department;
}
