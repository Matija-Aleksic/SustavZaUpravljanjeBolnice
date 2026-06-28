package com.alex.sustavzaupravljanjebolnice.entity.exception;

import java.security.NoSuchAlgorithmException;

public class CryptoAlgorithmException extends PasswordManagerException {
    public CryptoAlgorithmException(String message, NoSuchAlgorithmException e) {
        super(e.getMessage().concat(message), e);
    }

    public CryptoAlgorithmException(String message) {
        super(message);
    }

    public CryptoAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoAlgorithmException(Throwable cause) {
        super(cause);
    }

    public CryptoAlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CryptoAlgorithmException() {
    }
}
