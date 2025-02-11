package com.example.project_module5.exception;

public class IllegalEmailException extends RuntimeException {
    public IllegalEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalEmailException(String msg) {
        super(msg);
    }
}
