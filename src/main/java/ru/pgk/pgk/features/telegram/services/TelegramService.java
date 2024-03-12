package ru.pgk.pgk.features.telegram.services;

public interface TelegramService {

    void sendMessageNewSchedule(Short departmentId, Integer scheduleId);

    void sendMessage(Long telegramId, String message);
}
