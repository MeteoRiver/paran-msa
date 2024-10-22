package com.paranmanzang.groupservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorField {
    private Object value;
    private String message;
}