package com.moonie.authorization.common.exception.handler;

import com.moonie.authorization.common.exception.CustomException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionContollerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CustomException customException) {
        return ResponseEntity.status(customException.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(customException.getStatus(),
                                       customException.getErrorCode(),
                                       customException.getMessage()));
    }
}
