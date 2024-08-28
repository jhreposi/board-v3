package com.example.board.global.exception;

public class PasswordNotMatched extends RuntimeException {
    public PasswordNotMatched() {
    }

    public PasswordNotMatched(String message) {
        super(message);
    }

    public PasswordNotMatched(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMatched(Throwable cause) {
        super(cause);
    }

    public PasswordNotMatched(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
