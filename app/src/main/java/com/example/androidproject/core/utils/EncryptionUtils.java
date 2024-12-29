package com.example.androidproject.core.utils;

import android.annotation.SuppressLint;
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class EncryptionUtils {
    private final static String AES = "AES";
    private final static String SHA_256 = "SHA-256";
    private final static String CHARSET = "UTF-8";

    public static String encrypt(String data, String secretKey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(prepareSecretKey(secretKey), AES);
            @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes(CHARSET));
            return Base64.encodeToString(encryptedData, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptedData, String secretKey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(prepareSecretKey(secretKey), AES);
            @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decodedData = Base64.decode(encryptedData, Base64.DEFAULT);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] prepareSecretKey(String secret) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(SHA_256);
        byte[] hash = digest.digest(secret.getBytes(CHARSET));
        return Arrays.copyOfRange(hash, 0, 16);
    }

    public static String generateSecretKey(String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] hash = digest.digest(secret.getBytes(CHARSET));
            return Base64.encodeToString(Arrays.copyOfRange(hash, 0, 16), Base64.DEFAULT); // 256-bit to 128-bit
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
