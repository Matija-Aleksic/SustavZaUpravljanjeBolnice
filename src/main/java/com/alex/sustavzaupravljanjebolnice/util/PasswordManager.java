package com.alex.sustavzaupravljanjebolnice.util;

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
    private final Properties properties = new Properties();
    private final File file;

    /**
     * Instantiates a new Password manager using a standardized File object.
     * Pass an external file path (e.g., "passwords.properties" or System.getProperty("user.home") + "/passwords.properties")
     *
     * @param file the file reference to use for reading/writing storage
     * @throws IOException the io exception
     */
    public PasswordManager(File file) throws IOException {
        this.file = file;
        if (!file.exists()) {
            file.createNewFile();
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
     * @throws Exception the exception
     */
    public void savePassword(String username, String password) throws Exception {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        String value = salt + ":" + hash;
        properties.setProperty(username, value);
        saveToFile();
    }

    /**
     * Verify password boolean safely using constant-time evaluation.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean verifyPassword(String username, String password) throws Exception {
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
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashed = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashed);
    }
}