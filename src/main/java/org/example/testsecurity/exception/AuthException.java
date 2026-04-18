package org.example.testsecurity.exception;

import lombok.Getter;
import org.example.testsecurity.response.errors_code.AuthError;


@Getter
public class AuthException extends RuntimeException {

    private final AuthError authError;

    public AuthException(AuthError authError) {
        super(authError.getError());
        this.authError = authError;
    }
}
