package com.alex.sustavzaupravljanjebolnice.entity.exception;

public class InvalidBuilderStateException extends RuntimeException {
    public InvalidBuilderStateException(String message) {
        super(message);
    }

    public InvalidBuilderStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBuilderStateException(Throwable cause) {
        super(cause);
    }

    public InvalidBuilderStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidBuilderStateException() {
    }
}