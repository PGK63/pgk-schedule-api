package ru.pgk.pgk.features.schedule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.schedule.dto.ScheduleColumnDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleRowDto;
import ru.pgk.pgk.features.schedule.dto.ScheduleStudentDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final String scriptUrl = "http://localhost:5000/script";

    private ObjectMapper objectMapper = new ObjectMapper();

    public ScheduleRowDto getByGroupName(String name) {
        List<ScheduleRowDto> rows = get().rows().stream().filter(row -> row.group_name().equals(name)).toList();
        if(rows.isEmpty())
            throw new ResourceNotFoundException("Schedule not found");
        return rows.stream().findFirst().get();
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
