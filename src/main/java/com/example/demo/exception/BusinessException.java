package com.example.demo.exception;


public class BusinessException extends RuntimeException{
    private int code;

    public int getCode() {
        return code;
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

}
