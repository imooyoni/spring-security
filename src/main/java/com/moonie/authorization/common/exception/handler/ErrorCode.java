package com.moonie.authorization.common.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // COMMON
    INTERNAL_SERVER_ERROR(500, "internal.server.error"),
    NOT_IMPLEMENTED(501, "not.implemented"),

    // LOGIN
    LOGIN_NO_EXIST_USER(1, "login.no.exist.user");

    private final int serverStatus;
    private final String messageKey;
}
