package com.example.json_view_example.controller.advice;

import com.example.json_view_example.domain.dto.response.ExceptionResponse;
import com.example.json_view_example.exception.AlreadyExistException;
import com.example.json_view_example.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(Exception e) {
        return createResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, AlreadyExistException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex) {
        String cause = (ex instanceof MethodArgumentNotValidException exception) ?
                Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage() : ex.getMessage();
        return createResponseEntity(HttpStatus.BAD_REQUEST, cause);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleCommonException(Exception e) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(HttpStatus status, String message) {
        ExceptionResponse body = ExceptionResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(body);
    }

}
