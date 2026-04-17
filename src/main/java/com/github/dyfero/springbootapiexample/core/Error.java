package com.github.dyfero.springbootapiexample.core;

import lombok.Getter;

@Getter
public enum Error {

    VALIDATION_ERROR(1000, "Validation failed"),
    AUTHENTICATION_ERROR(2000, "Authentication error"),
    INVALID_CREDENTIALS_ERROR(2001, "Invalid username or password"),
    USER_NOT_VERIFIED_ERROR(3000, "User not verified"),
    NOT_FOUND_ERROR(4000, "Not found");

    private final Integer code;

    private final String message;

    Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
