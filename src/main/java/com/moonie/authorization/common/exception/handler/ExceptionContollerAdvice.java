package com.moonie.authorization.common.exception.handler;

import com.moonie.authorization.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@Slf4j
@ControllerAdvice
public class ExceptionContollerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("internal.server.error", null, locale);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(errorMessage);
        errorResponse.setErrName(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return ResponseEntity.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = customException.getMessage(messageSource, locale);

        log.error("locale: {}, url: {}, message: {}", locale, request.getRequestURI(), errorMessage);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(customException.getStatus());
        errorResponse.setMessage(errorMessage);
        errorResponse.setErrCode(customException.getErrCode());
        errorResponse.setErrName(customException.getErrName());

        return ResponseEntity.status(customException.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
