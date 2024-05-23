package ru.pgk.pgk.features.telegram.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;
import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherColumnDto;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherRowDto;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleColumn;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.student.services.StudentService;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;
import ru.pgk.pgk.features.teacher.service.user.TeacherUserService;
import ru.pgk.pgk.features.telegram.services.network.TelegramNetworkService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final StudentService studentService;
    private final TeacherUserService teacherUserService;
    private final ScheduleService scheduleService;

    private final TelegramNetworkService telegramNetworkService;

    private final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("EE, d MMMM yyyy");

    @Override
    @Transactional(readOnly = true)
    public void sendMessageNewSchedule(Short departmentId, Integer scheduleId) {
        List<StudentEntity> students = studentService.getAllByTelegramNotNull(departmentId);
        List<TeacherUserEntity> teachers = teacherUserService.getAllByTelegramNotNull(departmentId);

        for(StudentEntity student : students) {
            try {
                ScheduleStudentResponse response = scheduleService.getByStudent(scheduleId, student);
                sendMessage(student.getUser().getTelegram().getTelegramId(), getMessageNewScheduleStudent(response));
                System.out.println(student.getId());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        for(TeacherUserEntity teacher : teachers) {
            try {
                ScheduleTeacherResponse response = scheduleService.getByTeacher(scheduleId, teacher.getTeacher());
                sendMessage(teacher.getUser().getTelegram().getTelegramId(), getMessageNewScheduleTeacher(response));
                System.out.println(teacher.getId());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    @SneakyThrows
    public Boolean sendMessage(Long telegramId, String message) {
        Call<ResponseBody> call = telegramNetworkService.sendMessage(message, telegramId);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }

    private String getMessageNewScheduleTeacher(ScheduleTeacherResponse response) {
        String message = response.date().format(pattern);
        for(ScheduleTeacherRowDto row : response.rows()) {
            message += row.group_name() + " (" + row.shift()+ ")";
            for(ScheduleTeacherColumnDto column : row.columns()) {
                message += "\n\n\uD83D\uDD52 Пара: " + column.number()  +
                        "\n\uD83C\uDFE2 Кабинет: " + column.cabinet();
                if(column.exam()) message += "\n\uD83D\uDCCC Экзамен";
            }
            message += "\n\n\n";
        }
        return message;
    }

    private String getMessageNewScheduleStudent(ScheduleStudentResponse response) {
        StringBuilder message = new StringBuilder(response.date().format(pattern) + " (" + response.shift() + ")");
        for(ScheduleColumn column : response.columns()) {
            String number = "\n\n🕒 Пара: " + column.getNumber();
            if(column.getExam()) number += " (\uD83D\uDCCCЭкзамен)";
            String cabinet = "\n\uD83C\uDFE2 Кабинет: " + (column.getCabinet() != null ? column.getCabinet() : "Не указан");
            String teacher = "\n\uD83D\uDC64 Преподаватель: " + (column.getTeacher() != null ? column.getTeacher() : "Не указан");

            message.append(number).append(cabinet).append(teacher);
            if(column.getExam()) message.append("\n\uD83D\uDCCC Экзамен");
        }
        return message.toString();
    }
}
