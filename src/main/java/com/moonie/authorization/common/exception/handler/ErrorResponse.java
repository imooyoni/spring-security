package com.moonie.authorization.common.exception.handler;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus httpStatus;
    private ErrorCode errorCode;
    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
    private ErrorResponse(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse of(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus, message);
    }
    public static ErrorResponse of(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        return new ErrorResponse(httpStatus, errorCode, message);
    }
}
