package ru.pgk.main_service.features.schedule.dto.script;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScheduleColumnDto implements Serializable {

    private Integer number;
    private String teacher;
    private String cabinet;
    private Boolean exam;
}
