package com.example.project_module5.exception;

public class IllegalDateException extends IllegalArgumentException{
    public IllegalDateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalDateException(String msg) {
        super(msg);
    }
}
