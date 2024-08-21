package ru.pgk.pgk;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication
public class PgkApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Samara"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PgkApplication.class, args);
    }

}	
