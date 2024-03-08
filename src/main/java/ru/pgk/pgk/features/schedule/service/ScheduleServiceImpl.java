package ru.pgk.pgk.features.schedule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.schedule.dto.ScheduleColumnDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleRowDto;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherColumnDto;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.service.TeacherService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final TeacherService teacherService;

    private final String scriptUrl = "http://localhost:5000/script";

    private ObjectMapper objectMapper = new ObjectMapper();

    public ScheduleRowDto getByGroupName(String name) {
        List<ScheduleRowDto> rows = get().rows().stream().filter(row -> row.group_name().equals(name)).toList();
        if(rows.isEmpty())
            throw new ResourceNotFoundException("Schedule not found");
        return rows.stream().findFirst().get();
    }

    public List<ScheduleTeacherColumnDto> getByTeacherTelegramId(Long id) {
        TeacherEntity teacher = teacherService.getByTelegramId(id);
        return getByTeacher(teacher);
    }

    private List<ScheduleTeacherColumnDto> getByTeacher(TeacherEntity teacher) {
       ScheduleDto schedule = get();
       List<ScheduleTeacherColumnDto> teacherColumns = new ArrayList<>();

       for(ScheduleRowDto row : schedule.rows()) {
           for (ScheduleColumnDto column : row.columns()) {
               if((teacher.getCabinet() != null &&
                       column.cabinet().contains(teacher.getCabinet()) ||
                       column.teacher().equals(teacher.getFIO()))
               ) {
                   ScheduleTeacherColumnDto teacherColumn = new ScheduleTeacherColumnDto(
                           column.number(),
                           row.shift(),
                           row.group_name(),
                           column.cabinet()
                   );
                   teacherColumns.add(teacherColumn);
               }
           }
       }

       return teacherColumns.stream().sorted(Comparator.comparing(ScheduleTeacherColumnDto::number)).toList();
    }

    @SneakyThrows
    public ScheduleDto get() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scriptUrl))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), ScheduleDto.class);
    }
}
