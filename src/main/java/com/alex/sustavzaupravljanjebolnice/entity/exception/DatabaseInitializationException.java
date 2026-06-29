package com.alex.sustavzaupravljanjebolnice.entity.exception;


/**
 * Checked exception thrown when the database fails to initialize properly,
 * such as missing script files or configuration errors.
 */
public class DatabaseInitializationException extends Exception {
    /**
     * Instantiates a new Database initialization exception.
     *
     * @param message the message
     */
    public DatabaseInitializationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Database initialization exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DatabaseInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Database initialization exception.
     *
     * @param cause the cause
     */
    public DatabaseInitializationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Database initialization exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public DatabaseInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Database initialization exception.
     */
    public DatabaseInitializationException() {
    }
}