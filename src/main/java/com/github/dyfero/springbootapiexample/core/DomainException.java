package com.github.dyfero.springbootapiexample.core;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

@Getter
public final class DomainException extends ErrorResponseException {

    private final Error error;

    public DomainException(Error error) {
        super(httpStatus(error), problemDetail(error), null);
        this.error = error;
    }

    private static ProblemDetail problemDetail(Error error) {
        HttpStatus status = httpStatus(error);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, error.getMessage());
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setProperty("code", error.getCode());
        return problemDetail;
    }

    private static HttpStatus httpStatus(Error error) {
        int group = error.getCode() / 1000;

        return switch (group) {
            case 0, 1 -> HttpStatus.BAD_REQUEST;
            case 2 -> HttpStatus.UNAUTHORIZED;
            case 3 -> HttpStatus.FORBIDDEN;
            case 4 -> HttpStatus.NOT_FOUND;
            case 5 -> HttpStatus.UNPROCESSABLE_ENTITY;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
