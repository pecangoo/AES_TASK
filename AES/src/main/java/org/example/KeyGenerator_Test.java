package org.example;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGenerator_Test {

    public static byte[] generateKey() {
        byte[] key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            key = keyGenerator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}
