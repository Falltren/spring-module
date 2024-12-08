package com.fallt.spring_data_jdbc.controller.advice;

import com.fallt.spring_data_jdbc.domain.dto.response.ExceptionResponse;
import com.fallt.spring_data_jdbc.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception e) {
        ExceptionResponse body = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
