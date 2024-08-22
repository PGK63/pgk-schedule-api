package ru.pgk.main_service.configurations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.pgk.main_service.features.telegram.services.network.TelegramNetworkService;

@Configuration
public class RetrofitConfig {

    @Value("${bot.telegram.token}")
    private String telegramBotToken;

    @Bean
    public Retrofit telegramRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.telegram.org/bot" + telegramBotToken + "/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public TelegramNetworkService telegramNetworkService(@Qualifier("telegramRetrofit") Retrofit retrofit) {
        return retrofit.create(TelegramNetworkService.class);
    }

}
