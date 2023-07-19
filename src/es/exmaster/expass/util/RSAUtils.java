package es.exmaster.expass.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtils {
    private static final int KEY_SIZE = 4096;

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    public static String encrypt(String message, PublicKey publicKey) throws Exception {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        BigInteger plaintext = new BigInteger(messageBytes);

        BigInteger exponent = ((RSAPublicKey) publicKey).getPublicExponent();
        BigInteger modulus = ((RSAPublicKey) publicKey).getModulus();

        BigInteger ciphertext = plaintext.modPow(exponent, modulus);

        byte[] encryptedBytes = ciphertext.toByteArray();
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        BigInteger ciphertext = new BigInteger(encryptedBytes);

        BigInteger exponent = ((RSAPrivateKey) privateKey).getPrivateExponent();
        BigInteger modulus = ((RSAPrivateKey) privateKey).getModulus();

        BigInteger plaintext = ciphertext.modPow(exponent, modulus);

        byte[] decryptedBytes = plaintext.toByteArray();
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static PublicKey loadPublicKeyFromFile(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] publicKeyBytes = new byte[fis.available()];
        fis.read(publicKeyBytes);
        fis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey loadPrivateKeyFromFile(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] privateKeyBytes = new byte[fis.available()];
        fis.read(privateKeyBytes);
        fis.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static void saveKeyToFile(Key key, String fileName) throws Exception {
        byte[] keyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(Base64.getEncoder().encode(keyBytes));
        fos.close();
    }
}
