/*
 * Copyright (c) 2018, kchadha
 */

package blockchain;/*
 * Copyright (c) 2018, kchadha
 */

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import user.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * blockchain.BlockChain singleton class
 *
 */
public class Ledger {
    public static final String USERNAME_KEY = "username";
    public static final String DOMAIN_KEY = "domain";
    public static final String PASSWORD_KEY = "password";
    public static final String HASH_KEY = "hash";
    public static final String PREVIOUS_HASH_KEY = "previous_hash";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String NONCE_VALUE_KEY = "nonce";

    private static Ledger ledgerInstance = new Ledger();
    public static int difficulty = 1;
    private List<Block> ledgerData;

    /**
     * Private constructor for singleton class
     */
    private Ledger() {
        this.ledgerData = new ArrayList();
    }

    // thread safe way of getting singleton instance
    public static Ledger getInstance() {
        if (ledgerInstance == null) {
            synchronized (Ledger.class) {
                if (ledgerInstance == null) {
                    ledgerInstance = new Ledger();
                }
            }
        }
        return ledgerInstance;
    }

    void performSync(List<Block> tempLedger) {
        this.ledgerData = tempLedger;
    }

    /**
     * Function to add the transaction
     *
     * A Transaction comprises of user.UserData and bunch of other information like
     * Hash, PreviousHash and Timestamp
     *
     * @param userData user.User Data object
     * @return Boolean, which indicates if adding of transaction to the blockchain was successful or not
     */
    public Boolean createTransaction(UserData userData){
        if (this.ledgerData.isEmpty()) {
            this.ledgerData.add(new Block(userData, "0").mineBlock(difficulty));
            System.out.println("Verified and added transaction, size: " + this.ledgerData.size());
            return Boolean.TRUE;
        }
        else if(Utils.isChainValid(this.ledgerData)){
            this.ledgerData.add(new Block(userData, getPreviousBlockHash()).mineBlock(difficulty));
            System.out.println("Verified and added transaction, size: " + (this.ledgerData.size()));
            return Boolean.TRUE;
        }
        System.err.println("Blockchain is NOT valid !!");
        return Boolean.FALSE;
    }

    /**
     * Helper function to get previous block hash
     *
     * @return String
     */
    private String getPreviousBlockHash() {
        return this.ledgerData.get(ledgerData.size() - 1).getHash();
    }


    /**
     * Overriding toString to print the blockchain in nice json format
     *
     * @return String
     */
    @Override
    public String toString(){
        if(this.ledgerData.isEmpty()){
            return "BLOCKCHAIN EMPTY\n";
        }

        JsonArray jsonArray = new JsonArray();
        for (Block currentBlock : this.ledgerData) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put(USERNAME_KEY, currentBlock.getUserData().getUsername())
                    .put(DOMAIN_KEY, currentBlock.getUserData().getDomain())
                    .put(PASSWORD_KEY, currentBlock.getUserData().getPassword())
                    .put(HASH_KEY, currentBlock.getHash())
                    .put(PREVIOUS_HASH_KEY, currentBlock.getPreviousHash())
                    .put(TIMESTAMP_KEY, currentBlock.getTimestamp())
                    .put(NONCE_VALUE_KEY, currentBlock.getNonce());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
       // return new GsonBuilder().setPrettyPrinting().create().toJson(jsonArray);
    }

    int numOfBlocks() {
        return this.ledgerData.size();
    }
}
