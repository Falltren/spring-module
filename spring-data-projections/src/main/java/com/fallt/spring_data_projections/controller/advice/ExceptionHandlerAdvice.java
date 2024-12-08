package com.fallt.spring_data_projections.controller.advice;

import com.fallt.spring_data_projections.domain.dto.response.ExceptionResponse;
import com.fallt.spring_data_projections.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
