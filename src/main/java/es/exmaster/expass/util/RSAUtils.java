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

    public static PrivateKey privateOfString(String s) throws Exception {
        byte[] privateKeyBytes = s.getBytes();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static PublicKey publicOfString(String s) throws Exception {
        byte[] publicKeyBytes = s.getBytes();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static String privateToBase64(PrivateKey pk) {
        return Base64.getEncoder().encodeToString(pk.getEncoded());
    }

    public static String publicToBase64(PublicKey pk) {
        return Base64.getEncoder().encodeToString(pk.getEncoded());
    }
}
