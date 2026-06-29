package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.exception.CryptoAlgorithmException;
import com.alex.sustavzaupravljanjebolnice.entity.exception.PasswordManagerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

/**
 * The type Password manager.
 */
public class PasswordManager {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final Properties properties = new Properties();
    private final File file;

    /**
     * Instantiates a new Password manager.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public PasswordManager(File file) throws IOException {
        this.file = file;
        if (!file.exists()) {
            boolean created = file.createNewFile();
            if (!created) {
                throw new IOException("Failed to create user credentials storage file.");
            }
        } else {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            }
        }
    }

    /**
     * Save password.
     *
     * @param username the username
     * @param password the password
     * @throws PasswordManagerException the specific custom exception replacing generic Exception
     * @throws IOException              the io exception
     */
    public void savePassword(String username, String password) throws PasswordManagerException, IOException {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        String value = salt + ":" + hash;
        properties.setProperty(username, value);
        saveToFile();
    }

    /**
     * Verify password boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     * @throws PasswordManagerException the specific custom exception replacing generic Exception
     */
    public boolean verifyPassword(String username, String password) throws PasswordManagerException {
        String stored = properties.getProperty(username);
        if (stored == null) {
            return false;
        }
        String[] parts = stored.split(":");
        if (parts.length != 2) {
            return false;
        }
        String salt = parts[0];
        String storedHash = parts[1];
        String computedHash = hashPassword(password, salt);

        return MessageDigest.isEqual(storedHash.getBytes(), computedHash.getBytes());
    }

    private void saveToFile() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            properties.store(fos, "Hospital Management System User Credentials");
        }
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) throws CryptoAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoAlgorithmException("Encryption algorithm SHA-256 unavailable", e);
        }
    }
}