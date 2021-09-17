package luchocoin_package;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {

    public PublicKey publicKey;
    public PrivateKey privateKey;

    public Wallet() {

        generateKeyPair();

    }

    public void generateKeyPair() {

        try {

            KeyPairGenerator keyPairGen= KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair= keyPairGen.generateKeyPair();
            publicKey= pair.getPublic();
            privateKey= pair.getPrivate();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}