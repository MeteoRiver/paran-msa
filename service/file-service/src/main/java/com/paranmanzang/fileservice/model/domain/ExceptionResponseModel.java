package com.paranmanzang.fileservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(title = "예외 발생 정보")
public class ExceptionResponseModel {
    @Schema(title = "예외 코드")
    private String code;
    @Schema(title = "예외 메시지")
    private String message;
    @Schema(title = "오류 원인")
    private List<ErrorField> errors;
}
