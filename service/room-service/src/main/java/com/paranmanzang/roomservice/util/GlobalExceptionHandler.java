package com.paranmanzang.roomservice.util;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.paranmanzang.roomservice.model.domain.ErrorField;
import com.paranmanzang.roomservice.model.domain.ExceptionResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNoReadableException(HttpMessageNotReadableException e){
        List<ErrorField> errors= new ArrayList<>();
        if (e.getCause() instanceof InvalidFormatException){
            errors.add(new ErrorField(((InvalidFormatException) e.getCause()).getValue(), e.getMessage()));
        }

        return ResponseEntity.badRequest().body(
                new ExceptionResponseModel("NR", "HttpMessageNoReadableException", errors)
        );

    }

}