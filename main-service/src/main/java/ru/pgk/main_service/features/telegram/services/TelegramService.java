package ru.pgk.main_service.features.telegram.services;

public interface TelegramService {

    void sendMessageNewSchedule(Short departmentId, Integer scheduleId);

    Boolean sendMessage(Long telegramId, String message);
}
