package com.alex.sustavzaupravljanjebolnice.entity.exception;


/**
 * Checked exception thrown when the database fails to initialize properly,
 * such as missing script files or configuration errors.
 */
public class DatabaseInitializationException extends Exception {
    public DatabaseInitializationException(String message) {
        super(message);
    }

    public DatabaseInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseInitializationException(Throwable cause) {
        super(cause);
    }

    public DatabaseInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DatabaseInitializationException() {
    }
}