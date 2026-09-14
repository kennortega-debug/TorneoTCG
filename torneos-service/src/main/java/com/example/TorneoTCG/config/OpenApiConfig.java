package com.example.TorneoTCG.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "torneos-service",
                version = "1.0",
                description = "API para administrar torneos, rondas, participaciones, partidas, resultados y ranking"
        )
)
public class OpenApiConfig {
}
