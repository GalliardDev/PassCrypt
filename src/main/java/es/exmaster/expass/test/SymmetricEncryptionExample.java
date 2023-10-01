package es.exmaster.expass.test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class SymmetricEncryptionExample {
    public static void main(String[] args) {
        try {
            // Generate a symmetric key (AES key)
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // You can choose the key size (128, 192, or 256 bits)
            SecretKey secretKey = keyGenerator.generateKey();

            // Text to be encrypted
            String plainText = "Hello, World!";

            // Encrypt the text
            byte[] encryptedText = encrypt(plainText, secretKey);

            // Decrypt the text
            String decryptedText = decrypt(encryptedText, secretKey);

            //System.out.println("Original Text: " + plainText);
            //System.out.println("Encrypted Text: " + new String(encryptedText, StandardCharsets.UTF_8));
            //System.out.println("Decrypted Text: " + decryptedText);

            for(String s: List.of("Hola", "Mundo", "Adios")) {
                byte[] encrypted = encrypt(s, secretKey);
            	System.out.println(new String(encrypted, StandardCharsets.UTF_8));
                System.out.println(decrypt(encrypted, secretKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedText);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

