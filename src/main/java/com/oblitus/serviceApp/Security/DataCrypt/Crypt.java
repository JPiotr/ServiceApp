package com.oblitus.serviceApp.Security.DataCrypt;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

//todo: log public key?
public class Crypt {
    private final PrivateKey prk;

    public static PublicKey PublicKey;

    public Crypt() throws Exception {
        KeyPair keyPair = Asymetric.generateRIAKKeyPair();
        PublicKey = keyPair.getPublic();
        prk = keyPair.getPrivate();
    }

    public byte[] cryptData(String data) throws Exception {
        return Asymetric.RSAEncryption(data,prk);
    }

    public String decryptData(byte[] data) throws Exception {
        return Asymetric.RSADecryption(data,PublicKey);
    }
}
