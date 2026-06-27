package com.alex.sustavzaupravljanjebolnice.entity.exception;

import java.security.NoSuchAlgorithmException;

public class CryptoAlgorithmException extends PasswordManagerException {
    public CryptoAlgorithmException(String message, NoSuchAlgorithmException e) {
        super(e.getMessage().concat(message), e);
    }

}
