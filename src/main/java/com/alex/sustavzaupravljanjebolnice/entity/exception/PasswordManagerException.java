package com.alex.sustavzaupravljanjebolnice.entity.exception;

/**
 * The type Password manager exception.
 */
public class PasswordManagerException extends Exception {
    /**
     * Instantiates a new Password manager exception.
     *
     * @param message the message
     */
    public PasswordManagerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Password manager exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public PasswordManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Password manager exception.
     *
     * @param cause the cause
     */
    public PasswordManagerException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Password manager exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PasswordManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Password manager exception.
     */
    public PasswordManagerException() {
    }
}
