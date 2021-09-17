package luchocoin_package;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Block {

    public String hash;
    public ArrayList<Transaction> transaction;
    public String prevHash;
    public long timeStamp;
    public int nonce= 0;

    public Block(ArrayList<Transaction> transactions, String prevHash) {

        transaction= transactions;
        this.prevHash= prevHash;
        timeStamp= new Date().getTime();
        hash= getHash();

    }

    public Block(ArrayList<Transaction> transactions) {

        prevHash= "";
        transaction= transactions;
        timeStamp= new Date().getTime();
        hash= getHash();

    }

    public String getHash() {
        return Caesar.sha256(
            prevHash + Long.toString(timeStamp) + Integer.toString(nonce) + transaction.toString());
    }

    public void mineBlock(int difficulty) {

        char[] chars= new char[difficulty];
        Arrays.fill(chars, '0');
        String result= new String(chars);

        while (!hash.substring(0, difficulty).equals(result)) {
            // System.out.println("mining");
            nonce++ ;
            hash= getHash();
        }

    }

    public boolean hasValidTransactions() {

        for (Transaction indivTX : transaction) {

            if (!indivTX.verifySignature()) { return false; }

        }
        return true;

    }

}