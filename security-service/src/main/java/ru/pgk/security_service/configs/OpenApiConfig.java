package ru.pgk.security_service.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PGK Schedules API",
                version = "1.0.0"
        )
)
public class OpenApiConfig {}
