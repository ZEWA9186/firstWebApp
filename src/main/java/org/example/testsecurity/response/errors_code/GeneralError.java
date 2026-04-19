package org.example.testsecurity.response.errors_code;

import lombok.Getter;

@Getter
public enum GeneralError {
    VALIDATION_ERROR("Data validation error");

    private final String error;

    GeneralError(String error) {
        this.error = error;
    }
}
