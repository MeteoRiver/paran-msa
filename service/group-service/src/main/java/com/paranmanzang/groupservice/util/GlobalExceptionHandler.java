package com.paranmanzang.groupservice.util;


import com.paranmanzang.groupservice.model.domain.ErrorField;
import com.paranmanzang.groupservice.model.domain.ExceptionResponseModel;
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