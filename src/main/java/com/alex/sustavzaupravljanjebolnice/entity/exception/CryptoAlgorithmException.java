package com.alex.sustavzaupravljanjebolnice.entity.exception;

import java.security.NoSuchAlgorithmException;

/**
 * The type Crypto algorithm exception.
 */
public class CryptoAlgorithmException extends PasswordManagerException {
    /**
     * Instantiates a new Crypto algorithm exception.
     *
     * @param message the message
     * @param e       the e
     */
    public CryptoAlgorithmException(String message, NoSuchAlgorithmException e) {
        super(e.getMessage().concat(message), e);
    }

    /**
     * Instantiates a new Crypto algorithm exception.
     *
     * @param message the message
     */
    public CryptoAlgorithmException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Crypto algorithm exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CryptoAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Crypto algorithm exception.
     *
     * @param cause the cause
     */
    public CryptoAlgorithmException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Crypto algorithm exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public CryptoAlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Crypto algorithm exception.
     */
    public CryptoAlgorithmException() {
    }
}
