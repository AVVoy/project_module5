package com.example.project_module5.exception;

public class TickerNameNotFoundException extends RuntimeException{
    public TickerNameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TickerNameNotFoundException(String msg) {
        super(msg);
    }
}
