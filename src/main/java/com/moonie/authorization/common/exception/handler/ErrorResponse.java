package com.moonie.authorization.common.exception.handler;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private int status;
    private String errName;
    private String errCode;
    private String message;
    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String name, String code, String message){
        this.status = status;
        this.errName = name;
        this.errCode = code;
        this.message = message;
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message);
    }
    public static ErrorResponse of(int status, String name, String errCode, String message) {
        return new ErrorResponse(status,name,errCode,message);
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrName() { return errName; }
    public void setErrName(String errName) { this.errName = errName; }

    public String getErrCode() { return errCode; }
    public void setErrCode(String errCode) { this.errCode = errCode; }
}
