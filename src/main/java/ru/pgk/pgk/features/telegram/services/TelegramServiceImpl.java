package ru.pgk.pgk.features.telegram.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherColumnDto;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleColumn;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.student.services.StudentService;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.service.TeacherService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ScheduleService scheduleService;

    private final String telegramUrl = "https://api.telegram.org/bot5884965201:AAFiqkenkv-xVTf7GyzUu9sfwGFt5RumUtE/sendMessage?text=";

    @Override
    @Transactional(readOnly = true)
    public void sendMessageNewSchedule(Short departmentId, Integer scheduleId) {
        List<StudentEntity> students = studentService.getAll(departmentId);
        List<TeacherEntity> teachers = teacherService.getAll(departmentId);

        for(StudentEntity student : students) {
            ScheduleStudentResponse response = scheduleService.getByGroupName(scheduleId, student.getGroupName());
            sendMessage(student.getUser().getTelegramId(), getMessageNewScheduleStudent(response));
        }

        for(TeacherEntity teacher : teachers) {
            ScheduleTeacherResponse response = scheduleService.getByTeacher(scheduleId, teacher);
            sendMessage(teacher.getUser().getTelegramId(), getMessageNewScheduleTeacher(response));
        }
    }

    @Override
    @SneakyThrows
    public void sendMessage(Long telegramId, String message) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(telegramUrl + message + "&chat_id=" + telegramId))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String getMessageNewScheduleTeacher(ScheduleTeacherResponse response) {
        String message = response.date().toString();
        for(ScheduleTeacherColumnDto column : response.columns()) {
            message += "\n\n\uD83D\uDD52 Пара: " + column.number() + " (" + column.shift()+ " смена)\n" +
                    "\n\uD83C\uDFE2 Кабинет: " + column.cabinet() +
                    "\n\uD83D\uDC65 Группа: " + column.group_name();
            if(column.exam()) message += "\n\uD83D\uDCCC Экзамен";
        }
        return message;
    }

    private String getMessageNewScheduleStudent(ScheduleStudentResponse response) {
        StringBuilder message = new StringBuilder(response.date() + "(" + response.shift() + ")");
        for(ScheduleColumn column : response.columns()) {
            String number = "\n\n🕒 Пара: " + column.number();
            if(column.exam()) number += " (\uD83D\uDCCCЭкзамен)";
            String cabinet = column.cabinet() != null ? "\n\n\uD83C\uDFE2 Кабинет: " + column.cabinet() : "Не указан";
            String teacher = column.teacher() != null ? "\n\n\uD83D\uDC64 Преподаватель: " + column.teacher() : "Не указан";

            message.append(number).append(cabinet).append(teacher);
            if(column.exam()) message.append("\n\uD83D\uDCCC Экзамен");
        }
        return message.toString();
    }
}
