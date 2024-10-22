package com.paranmanzang.fileservice.util;

import com.amazonaws.SdkClientException;
import com.paranmanzang.fileservice.model.domain.ErrorField;
import com.paranmanzang.fileservice.model.domain.ExceptionResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<?> cloudException(SdkClientException e){
        List<ErrorField> errors= new ArrayList<>();
        errors.add(new ErrorField("", e.getMessage().substring(0, e.getMessage().indexOf("("))));
        return ResponseEntity.badRequest().body(
                new ExceptionResponseModel("CE", "SdkClientException", errors)
        );
    }

}