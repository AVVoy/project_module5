package com.example.project_module5.exception;

public class IllegalUsernameException extends RuntimeException {
    public IllegalUsernameException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalUsernameException(String msg) {
        super(msg);
    }
}
