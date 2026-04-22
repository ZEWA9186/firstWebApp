package org.example.testsecurity.response.errors_code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getCode();
    String name();
}
