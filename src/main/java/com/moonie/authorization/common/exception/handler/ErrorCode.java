package com.moonie.authorization.common.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400
    UNKNOWN(000, "알 수 없는 에러가 발생했습니다.")
    ;

    private final int serverErrcode;
    private final String serverMsg;
}
