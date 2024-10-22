package com.paranmanzang.userservice.config;

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
                title = "User Service",
                description = "User service 관련 swagger",
                version = "v1"
        ),
        tags = {
                @Tag(name = "01. AdminPost", description = "관리자 게시판 기능"),
                @Tag(name = "02. DeclarationPost", description = "신고 게시판 기능"),
                @Tag(name = "03. Friend", description = "친구 관리 기능"),
                @Tag(name = "04. Reissue", description = "토큰 재발급 기능"),
                @Tag(name = "05. User", description = "유저 관련 기능"),
                @Tag(name = "06. LikeBook", description = "책 찜 기능"),
                @Tag(name = "07. LikePost", description = "게시물 좋아요 기능"),
                @Tag(name = "08. LikeRoom", description = "공간 찜 기능")
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
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}