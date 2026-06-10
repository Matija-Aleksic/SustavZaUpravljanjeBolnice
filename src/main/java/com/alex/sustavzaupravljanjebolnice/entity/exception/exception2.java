package com.alex.sustavzaupravljanjebolnice.entity.exception;

/**
 * The type Exception 2.
 */
public class exception2 extends RuntimeException {
    /**
     * Instantiates a new Exception 2.
     *
     * @param message the message
     */
    public exception2(String message) {
        super(message);
    }

    /**
     * Instantiates a new Exception 2.
     *
     * @param message the message
     * @param cause   the cause
     */
    public exception2(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Exception 2.
     *
     * @param cause the cause
     */
    public exception2(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Exception 2.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public exception2(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Exception 2.
     */
    public exception2() {
    }
}
