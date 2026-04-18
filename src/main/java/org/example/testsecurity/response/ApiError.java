package org.example.testsecurity.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {
    private String message;
    private String code;
}
