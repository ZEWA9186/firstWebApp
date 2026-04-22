package org.example.testsecurity.response.errors_code;

import lombok.Getter;

@Getter
public enum GeneralError implements ErrorCode {
    VALIDATION_ERROR("Data validation error"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private final String errorCode;

    GeneralError(String errorCode) {
        this.errorCode = errorCode;
    }

}
