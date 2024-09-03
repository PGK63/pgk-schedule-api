package ru.pgk.main_service.features.schedule.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleRowDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "schedules")
public class ScheduleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<ScheduleRowDto> rows;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentEntity department;
}
