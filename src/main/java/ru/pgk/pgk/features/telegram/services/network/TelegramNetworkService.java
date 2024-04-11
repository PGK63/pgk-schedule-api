package ru.pgk.pgk.features.telegram.services.network;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TelegramNetworkService {

    @POST("/sendMessage")
    void sendMessage(
            @Query("text") String text,
            @Query("chat_id") Long chatId
    );
}
