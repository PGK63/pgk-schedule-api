package ru.pgk.main_service.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@OpenAPIDefinition(
        info = @Info(
                title = "PGK Schedules API",
                version = "1.0.0"
        )
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "bearerAuth",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "bearerAuth  (http, Bearer)",
                        description = "JWT Authorization header using the Bearer scheme",
                        scheme = "bearer"
                )
        }
)
public class OpenApiConfig {}
