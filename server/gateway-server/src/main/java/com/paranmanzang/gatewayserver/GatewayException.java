package com.paranmanzang.gatewayserver;

import com.paranmanzang.gatewayserver.Enum.ExceptionStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GatewayException extends RuntimeException {
    private final HttpStatus status;

    // HttpStatus로 직접 생성
    public GatewayException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    // ExceptionStatus로 생성
    public GatewayException(ExceptionStatus exceptionStatus, String message) {
        super(message);
        this.status = HttpStatus.valueOf(exceptionStatus.getStatus()); // HttpStatus 반환
    }
}