package ru.pgk.pgk.features.telegram.services;

public interface TelegramService {

    Boolean sendMessageNewSchedule(Short departmentId, Integer scheduleId);

    Boolean sendMessage(Long telegramId, String message);
}
