package org.example.testsecurity.response.errors_code;

import lombok.Getter;

@Getter
public enum AuthError {
    REGISTRATION_FAILED("Email already exists"),
    INVALID_CREDENTIALS("Invalid credentials");
    private final String error;

    AuthError(String error) {
        this.error = error;
    }
}
