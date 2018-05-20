/*
 * Copyright (c) 2018, kchadha
 */

package blockchain;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import user.UserData;

import java.util.ArrayList;
import java.util.List;

import static blockchain.Ledger.*;

public class Utils {
    /**
     * Helper function to evaluate of the blockchain is valid so far
     * THis function is called everytime an attempt to add a transaction is made
     *
     * @return Boolean, if the chain is valid or not
     */
    public static Boolean isChainValid(List<Block> ledger){

        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for (int i = 1; i < ledger.size(); i++) {
            Block currentBlock = ledger.get(i);
            Block previousBlock = ledger.get(i-1);

            // check if current hash are valid
            String actualHash = currentBlock.generateHash();
            if(!currentBlock.getHash().equals(actualHash)) {
                System.out.println("Current block actual hash: " + actualHash);
                System.out.println("Current block saved hash: " + currentBlock.getHash());

                return Boolean.FALSE;
            }

            // check if previous hash are valid
            actualHash = previousBlock.generateHash();
            if(!previousBlock.generateHash().equals(currentBlock.getPreviousHash())){
                System.out.println("Previous block actual hash: " + actualHash);
                System.out.println("Previous block saved hash: " + currentBlock.getPreviousHash());
                return Boolean.FALSE;
            }

            //check if the block was really mined
            if(!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * @param jsonArray
     * @return
     */
    public static Boolean syncLedger(JsonArray jsonArray) {
        List<Block> tempLedger = new ArrayList<>();
        Boolean status = Boolean.FALSE;
        System.out.println("Number of blocks received: " + jsonArray.size());
        for (Object jsonObject : jsonArray){
            String username = ((JsonObject) jsonObject).getString(USERNAME_KEY);
            String domain = ((JsonObject) jsonObject).getString(DOMAIN_KEY);
            String password = ((JsonObject) jsonObject).getString(PASSWORD_KEY);
            String hash = ((JsonObject) jsonObject).getString(HASH_KEY);
            String previous_hash = ((JsonObject) jsonObject).getString(PREVIOUS_HASH_KEY);
            String timestamp = ((JsonObject) jsonObject).getString(TIMESTAMP_KEY);
            String nonce = ((JsonObject) jsonObject).getString(NONCE_VALUE_KEY);
            UserData userData = new UserData(domain, username, password);
            tempLedger.add(new Block(userData, hash, previous_hash, timestamp, nonce));
        }

        if(tempLedger.size() > Ledger.getInstance().numOfBlocks()){
            boolean validationStatus = isChainValid(tempLedger);
            if(validationStatus) {
                Ledger.getInstance().performSync(tempLedger);
                status = Boolean.TRUE;
            }else{
                status = Boolean.FALSE;
            }
            System.out.println("SyncBlockchain: ledger validation status - " + validationStatus);
        }
        return status;
    }

    /**
     * @param data
     * @return
     */
    public static Boolean addTransaction(JsonObject data) {
        String username = data.getString(USERNAME_KEY);
        String domain = data.getString(DOMAIN_KEY);
        String password = data.getString(PASSWORD_KEY);
        return Ledger.getInstance().createTransaction(new UserData(domain, username, password));
    }
}
