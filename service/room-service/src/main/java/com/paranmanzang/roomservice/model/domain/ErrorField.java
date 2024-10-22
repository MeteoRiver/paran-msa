package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(title = "오류 정보")
public class ErrorField {
    @Schema(title ="오류 발생한 값")
    private Object value;
    @Schema(title = "발생 원인")
    private String message;
    
}
