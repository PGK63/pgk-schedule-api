package ru.pgk.pgk.features.schedule.service.script.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;

import java.util.List;

public interface ScheduleScriptNetworkService {

    @GET("pgk/schedule/script")
    Call<List<Schedule>> getSchedules(
            @Query("next_date") Boolean nextDate,
            @Query("department_id") Short departmentId
    );
}
