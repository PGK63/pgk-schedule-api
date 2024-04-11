package ru.pgk.pgk.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.pgk.pgk.features.schedule.service.script.network.ScheduleScriptNetworkService;
import ru.pgk.pgk.features.telegram.services.network.TelegramNetworkService;

@Configuration
public class RetrofitConfig {

    @Value("${bot.telegram.token}")
    private String telegramBotToken;

    @Value("${schedule.script.url}")
    private String scriptBaseUrl;

    @Bean
    public Retrofit scheduleScriptRetrofit() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        return new Retrofit.Builder()
                .baseUrl(scriptBaseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

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

    @Bean
    public ScheduleScriptNetworkService scheduleScriptNetworkService(@Qualifier("scheduleScriptRetrofit") Retrofit retrofit) {
        return retrofit.create(ScheduleScriptNetworkService.class);
    }
}
