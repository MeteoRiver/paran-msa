package com.paranmanzang.commentservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExceptionResponseModel {
    private String code;
    private String message;
    private List<ErrorField> errors;
}
