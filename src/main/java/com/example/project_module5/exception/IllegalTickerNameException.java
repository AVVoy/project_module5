package com.example.project_module5.exception;

public class IllegalTickerNameException extends RuntimeException{
    public IllegalTickerNameException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalTickerNameException(String msg) {
        super(msg);
    }
}
