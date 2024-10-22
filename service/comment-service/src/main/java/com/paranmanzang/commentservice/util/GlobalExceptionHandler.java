package com.paranmanzang.commentservice.util;

import com.paranmanzang.commentservice.model.domain.ErrorField;
import com.paranmanzang.commentservice.model.domain.ExceptionResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException e) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponseModel("BE", "Bind Exception", e.getFieldErrors().stream()
                        .map(fieldError -> new ErrorField(
                                fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage()))
                        .toList()));
    }

}