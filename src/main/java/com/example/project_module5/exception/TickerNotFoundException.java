package com.example.project_module5.exception;

public class TickerNotFoundException extends RuntimeException{
    public TickerNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TickerNotFoundException(String msg) {
        super(msg);
    }
}
