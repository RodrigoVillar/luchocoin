package luchocoin_package;

import java.security.PublicKey;
import java.util.ArrayList;

public class LuchoChain {

    public ArrayList<Block> luchochain;
    public ArrayList<Transaction> pendingTransactions;
    public int miningReward;
    public int difficulty;

    public LuchoChain() {
        difficulty= 4;
        miningReward= 100;
        luchochain= new ArrayList<>();
        pendingTransactions= new ArrayList<>();
    }

    public Boolean isChainValid() {

        for (int i= 1; i < luchochain.size(); i++ ) {

            Block currentBlock= luchochain.get(i);
            Block prevBlock= luchochain.get(i - 1);

            if (!currentBlock.hasValidTransactions()) { return false; }
            if (currentBlock.prevHash != prevBlock.hash) { return false; }
            if (currentBlock.hash != currentBlock.getHash()) { return false; }

        }

        return true;
    }

    public void minePendingTransactions(PublicKey miningRewardAddress) {

        Block block= new Block(pendingTransactions);
        block.mineBlock(difficulty);

        System.out.println("Block successfully mined");
        luchochain.add(block);

        pendingTransactions.clear(); // Resets pending transactions

        // Gives miner their reward, to be mined in next block
        pendingTransactions.add(new Transaction(null, miningRewardAddress, miningReward));

    }

    public Block latestBlock() {

        return luchochain.get(luchochain.size() - 1);

    }

    public void addTransaction(Transaction transaction) {

        if (transaction.fromAddress == null || transaction.toAddress == null) {
            throw new RuntimeException("Addresses improperly filled");
        }
        if (!transaction.verifySignature()) {
            throw new RuntimeException("Transaction cannot be processed");
        }

        pendingTransactions.add(transaction);

    }

    public float getBalanceOfAddress(PublicKey address) {
        float balance= 0;

        // Loops over each block of chain
        for (Block outerElement : luchochain) {

            // Loops over each transaction of the block
            for (Transaction innerElement : outerElement.transaction) {
                if (innerElement.fromAddress == address) {
                    balance-= innerElement.amount;
                }
                if (innerElement.toAddress == address) {
                    balance+= innerElement.amount;
                }
            }

        }

        return balance;

    }

    public Block createGenesisBlock() {
        return new Block(null, "0"); // May have to change argument for prevTransaction parameter
    }

}