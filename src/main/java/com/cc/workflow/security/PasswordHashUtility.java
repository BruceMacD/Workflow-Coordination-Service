package com.cc.workflow.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javafx.util.Pair;

@Service
public class PasswordHashUtility {
    private static int ITERATIONS = 10000;
    private static int KEY_LENGTH = 256;

    private SecretKeyFactory keyFactory;
    private SecureRandom secureRandom;

    public PasswordHashUtility() throws NoSuchAlgorithmException {
        keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        secureRandom = new SecureRandom();
    }

    public Pair<String, String> getHashedPasswordAndSalt(String password) {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

        try {
            return new Pair<>(
                    Base64.encodeBase64String(salt),
                    Base64.encodeBase64String(keyFactory.generateSecret(spec).getEncoded()));
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean passwordIsValid(String password, String salt, String hashedValue) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.decodeBase64(salt), ITERATIONS, KEY_LENGTH);

        try {
            return hashedValue.equals(Base64.encodeBase64String(keyFactory.generateSecret(spec).getEncoded()));
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}