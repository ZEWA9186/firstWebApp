package org.example.testsecurity.exception;

import lombok.Getter;
import org.example.testsecurity.response.errors_code.ErrorCode;
import org.springframework.http.HttpStatus;


@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;
    private final String customMessage;

    public AuthException(ErrorCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.customMessage = message;
    }
    public AuthException(ErrorCode errorCode, HttpStatus status) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
        this.status = status;
        this.customMessage = errorCode.getCode();
    }
}