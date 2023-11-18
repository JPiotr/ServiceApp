package com.oblitus.serviceApp.Security.DataCrypt;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Scanner;

//https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/

public class Asymetric {
    private static final String RSA = "RSA";
    private static Scanner sc;

    public static KeyPair generateRIAKKeyPair()
            throws Exception
    {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using private Key.
    public static byte[] RSAEncryption(String plainText, PrivateKey privateKey)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(plainText.getBytes());
    }

    // Decryption function which converts
    // the ciphertext back to the
    // original plaintext.
    public static String RSADecryption(byte[] cipherText, PublicKey publicKey)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(cipherText);
        return new String(result);
    }
}
