package org.example.testsecurity.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.testsecurity.response.ApiError;
import org.example.testsecurity.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(AuthException ex) {
        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .code(ex.getAuthError().name())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(apiError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message("Чёт очень хуёвое, Логи чекни")
                .code("INTERNAL_SERVER_ERROR")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(apiError));
    }
}
