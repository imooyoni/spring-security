package com.moonie.authorization.common.exception;

import com.moonie.authorization.common.exception.handler.ErrorCode;
import lombok.Getter;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getStatus() {
        return errorCode.getServerStatus();
    }

    public String getMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage(errorCode.getMessageKey(), null, locale);
    }
}
