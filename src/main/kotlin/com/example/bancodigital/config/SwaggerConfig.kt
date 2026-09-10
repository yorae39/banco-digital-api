package com.example.bancodigital.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi // Import correto para Spring Boot 3 / Springdoc v2
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    private val bearerAuth = "bearerAuth"

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public")
            .packagesToScan("com.example.bancodigital.controller")
            .pathsToMatch("/**")
            .build()
    }

    @Bean
    fun adminApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin")
            .packagesToScan("com.example.bancodigital.admin")
            .pathsToMatch("/internal/**")
            .build()
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("DIGITAL BANK WEB REST API")
                    .description("Product API to perform CRUD operations")
                    .version("1.0")
                    .termsOfService("Terms of service")
                    .contact(
                        Contact()
                            .name("Luiz Paulo Aureliano")
                            .email("lpaureliano74@gmail.com")
                    )
                    .license(
                        License()
                            .name("License of API")
                            .url("API license URL")
                    )
            )
            .addSecurityItem(SecurityRequirement().addList(bearerAuth))
            .components(
                Components().addSecuritySchemes(
                    bearerAuth,
                    SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
    }
}