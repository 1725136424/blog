package com.study.exception;

public class ErrorCodeException extends Exception{

    public ErrorCodeException() {
    }

    public ErrorCodeException(String message) {
        super(message);
    }

    public ErrorCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorCodeException(Throwable cause) {
        super(cause);
    }
}
