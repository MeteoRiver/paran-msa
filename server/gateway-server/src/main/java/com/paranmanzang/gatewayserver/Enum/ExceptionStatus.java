package com.paranmanzang.gatewayserver.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {
    UNAUTHORIZED(401),  // 인증 실패
    NO_PERMISSION(403),  // 권한 없음
    NOT_FOUND(404);      // 리소스 찾을 수 없음

    private final int status;
}