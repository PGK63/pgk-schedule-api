package ru.pgk.pgk.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;

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
                ),
                @SecurityScheme(
                        name = "X-API-KEY",
                        type = SecuritySchemeType.APIKEY,
                        in = SecuritySchemeIn.HEADER,
                        paramName = "X-API-KEY",
                        description = "API Key в формате UUID (например: 550e8400-e29b-41d4-a716-446655440000)"
                )
        }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("X-API-KEY"));
    }
}
