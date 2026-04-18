package org.example.testsecurity.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse<T> {
    private T data;
    private ApiError error;
    private boolean success;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().data(data).success(true).build();
    }

    public static <T> ApiResponse<T> error(ApiError error) {
        return ApiResponse.<T>builder().error(error).build();
    }
}
