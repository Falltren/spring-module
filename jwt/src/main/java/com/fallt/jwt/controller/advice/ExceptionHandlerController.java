package com.fallt.jwt.controller.advice;

import com.fallt.jwt.domain.dto.response.ExceptionResponse;
import com.fallt.jwt.exception.AlreadyExistException;
import com.fallt.jwt.exception.EntityNotFoundException;
import com.fallt.jwt.exception.RefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistException(Exception e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, RefreshTokenException.class})
    public ResponseEntity<ExceptionResponse> handleForbidden(Exception e) {
        return createResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String cause = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return createResponseEntity(HttpStatus.BAD_REQUEST, cause);
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(HttpStatus status, String message) {
        ExceptionResponse body = ExceptionResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
