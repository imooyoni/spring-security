package com.moonie.authorization.common.exception.handler;

import com.moonie.authorization.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ExceptionContollerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("internal.server.error", null, locale);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(500);
        errorResponse.setMessage(errorMessage);

        return ResponseEntity.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(HttpServletRequest request, CustomException customException) {
        Locale locale = request.getLocale();
        String errorMessage = customException.getMessage(messageSource, locale);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(customException.getStatus());
        errorResponse.setMessage(errorMessage);

        return ResponseEntity.status(customException.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
