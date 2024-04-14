package ru.pgk.pgk.features.schedule.entities.json;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScheduleColumn implements Serializable {

    private Integer number;
    private String teacher;
    private String cabinet;
    private Boolean exam;
}
