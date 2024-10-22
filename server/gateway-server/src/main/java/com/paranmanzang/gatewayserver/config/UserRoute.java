package com.paranmanzang.gatewayserver.config;

import com.paranmanzang.gatewayserver.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class UserRoute {
    @Bean
    public RouterFunction<ServerResponse> setUserRoute(UserController userController) {
        log.info("여기 도착");
        return  //회원가입
                route(POST("/api/users").and(accept(MediaType.APPLICATION_JSON)), userController::insert)
                        .andRoute(POST("/api/users/checkNickname").and(accept(MediaType.APPLICATION_JSON)), userController::checkNickname)
                        .andRoute(POST("/api/users/checkPassword").and(accept(MediaType.APPLICATION_JSON)), userController::checkPassword)
                        .andRoute(DELETE("/api/users").and(accept(MediaType.APPLICATION_JSON)), userController::remove)
                        .andRoute(PUT("/api/users/updatePassword").and(accept(MediaType.APPLICATION_JSON)), userController::updatePassword)
                        .andRoute(PUT("/api/users/updateLogoutUserTime").and(accept(MediaType.APPLICATION_JSON)), userController::updateLogoutUserTime)
                        .andRoute(PUT("/api/users/updateDeclaration").and(accept(MediaType.APPLICATION_JSON)), userController::updateDeclaration)
                        .andRoute(PUT("/api/users/updateRole").and(accept(MediaType.APPLICATION_JSON)), userController::updateRole)
                        .andRoute(GET("/api/users/findAllByNickname").and(accept(MediaType.APPLICATION_JSON)), userController::findAllByNickname)
                        .andRoute(GET("/api/users/findByNickname").and(accept(MediaType.APPLICATION_JSON)), userController::findByNickname)
                        .andRoute(GET("/api/users/checkRole").and(accept(MediaType.APPLICATION_JSON)), userController::checkRole)
                        .andRoute(GET("/api/users/checkUsername").and(accept(MediaType.APPLICATION_JSON)), userController::checkUsername);

    }
}



