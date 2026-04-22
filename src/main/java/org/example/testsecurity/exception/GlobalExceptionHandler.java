package org.example.testsecurity.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.testsecurity.response.ApiError;
import org.example.testsecurity.response.ApiResponse;
import org.example.testsecurity.response.errors_code.AuthError;
import org.example.testsecurity.response.errors_code.GeneralError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(AuthException ex) {
        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .code(ex.getErrorCode().name())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(ApiResponse.error(apiError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message("Чёт очень хуёвое, Логи чекни")
                .code("INTERNAL_SERVER_ERROR")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(apiError));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        ApiError apiError = ApiError.builder()
                .message(GeneralError.VALIDATION_ERROR.getErrorCode())
                .code(GeneralError.VALIDATION_ERROR.name())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .data(errors)
                        .error(apiError)
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUsernameNotFound(UsernameNotFoundException ex) {
        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .code(ex.getName())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(apiError));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message(AuthError.INVALID_CREDENTIALS.getErrorCode())
                .code(AuthError.INVALID_CREDENTIALS.name())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(apiError));
    }
}
