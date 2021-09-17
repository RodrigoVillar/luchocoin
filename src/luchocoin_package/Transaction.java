package luchocoin_package;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction {

    public PublicKey fromAddress;
    public PublicKey toAddress;
    public int amount;
    public byte[] signature;
    public String txHash;

    private static int sequence= 0;

    public Transaction(PublicKey fromAddress, PublicKey toAddress, int amount) {

        this.fromAddress= fromAddress;
        this.toAddress= toAddress;
        this.amount= amount;

        txHash= calculateHash();

    }

    private String calculateHash() {

        sequence++ ;

        return Caesar.sha256(fromAddress.toString() + toAddress.toString() + amount + sequence);

    }

    public void generateSignature(PrivateKey privateKey) {

        String data= Caesar.getStringFromKey(fromAddress) + Caesar.getStringFromKey(toAddress) +
            amount;
        signature= Caesar.applyRSA(privateKey, data);

    }

    public boolean verifySignature() {

        String data= Caesar.getStringFromKey(fromAddress) + Caesar.getStringFromKey(toAddress) +
            amount;
        return Caesar.verifyRSA(fromAddress, data, signature);

    }

}