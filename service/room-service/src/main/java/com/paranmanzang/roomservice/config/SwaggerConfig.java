package com.paranmanzang.roomservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Room Service API Docs",
                description = "room service 관련 swagger 입니다.",
                version = "v1"
        ),
        tags = {
                @Tag(name = "01. Room", description = "공간 기능"),
                @Tag(name = "02. Booking", description = "예약 기능"),
                @Tag(name = "03. Account", description = "결제 기능"),
                @Tag(name = "04. Review", description = "리뷰 기능"),
                @Tag(name = "05. Address", description = "주소 기능"),
                @Tag(name = "06. Address", description = "시간 기능"),
        }
)
@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "bearer";

    @Bean
    public OpenAPI openAPI() {
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
                .addSecuritySchemes(securityJwtName, new SecurityScheme()
                        .name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(securityJwtName));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}

