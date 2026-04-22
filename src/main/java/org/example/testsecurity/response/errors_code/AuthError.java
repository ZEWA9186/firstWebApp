package org.example.testsecurity.response.errors_code;

import lombok.Getter;

@Getter
public enum AuthError implements ErrorCode {
    REGISTRATION_FAILED("Email already exists"),
    INVALID_CREDENTIALS("Invalid credentials");

    private final String errorCode;

    AuthError(String error) {
        this.errorCode = error;
    }

    @Override
    public String getCode() {
        return errorCode;
    }
}
