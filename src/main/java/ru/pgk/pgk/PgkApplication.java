package ru.pgk.pgk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class PgkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgkApplication.class, args);
	}

}
