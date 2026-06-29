package com.alex.sustavzaupravljanjebolnice.entity.exception;

/**
 * The type Invalid builder state exception.
 */
public class InvalidBuilderStateException extends RuntimeException {
    /**
     * Instantiates a new Invalid builder state exception.
     *
     * @param message the message
     */
    public InvalidBuilderStateException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid builder state exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidBuilderStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Invalid builder state exception.
     *
     * @param cause the cause
     */
    public InvalidBuilderStateException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Invalid builder state exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public InvalidBuilderStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Invalid builder state exception.
     */
    public InvalidBuilderStateException() {
    }
}