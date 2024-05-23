package ru.pgk.pgk.features.telegram.services.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TelegramNetworkService {

    @POST("/sendMessage")
    Call<ResponseBody> sendMessage(
            @Query("text") String text,
            @Query("chat_id") Long chatId
    );
}
