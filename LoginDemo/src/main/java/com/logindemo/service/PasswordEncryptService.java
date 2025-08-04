package com.logindemo.service;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncryptService {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(char[] password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashed = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hashed);
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; 
        random.nextBytes(salt);
        return salt;
    }

    public static String hashWithGeneratedSalt(char[] password) throws Exception {
        byte[] salt = generateSalt();
        String hashed = hashPassword(password, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + hashed;
    }

    public static boolean validatePassword(char[] password, String storedSaltAndHash) throws Exception {
        String[] parts = storedSaltAndHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String hashedInput = hashPassword(password, salt);
        
        return hashedInput.trim().equals(parts[1].trim());
 
    }
}

