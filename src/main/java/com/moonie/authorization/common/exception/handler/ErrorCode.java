package com.moonie.authorization.common.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // COMMON
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "internal.server.error"),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "COMMON-501", "not.implemented"),

    // LOGIN ===400===
    USER_NO_EXIST_USER(HttpStatus.NOT_FOUND, "USER-001","user.no.exist.user"),
    USER_MISSING_USERNAME(HttpStatus.BAD_REQUEST, "USER-002","user.missing.username"),
    USER_MISSING_PASSWORD(HttpStatus.BAD_REQUEST, "USER-003","user.missing.password"),
    USER_MISSING_EMAIL(HttpStatus.BAD_REQUEST, "USER-004","user.missing.email"),
    USER_MISSING_PHONE(HttpStatus.BAD_REQUEST, "USER-005","user.missing.phone"),
    USER_MISSING_COUNTRYCODE(HttpStatus.BAD_REQUEST, "USER-006","user.missing.countrycode"),
    // SINGIN
    USER_EXIST_USER_INFO(HttpStatus.CONFLICT, "USER-007","user.exist.user.info"),
    // MODIFY
    USER_MISSING_USERID(HttpStatus.BAD_REQUEST, "USER-008","user.missing.userid")
    ;

    private final HttpStatus serverStatus;
    private final String serviceCode;
    private final String messageKey;
}
