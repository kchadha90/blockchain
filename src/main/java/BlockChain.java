/*
 * Copyright (c) 2018, kchadha
 */

import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * BlockChain singleton class
 *
 */
public class BlockChain {
    private List<Block> blockChain;
    private static int difficulty = 5;


    /**
     * Private constructor for singleton class
     */
    private BlockChain() {
        this.blockChain = new ArrayList<>();
    }

    public static BlockChain getBlockChain(){
        return new BlockChain();
    }

    /**
     * Function to add the transaction
     *
     * A Transaction comprises of UserData and bunch of other information like
     * Hash, PreviousHash and Timestamp
     *
     * @param userData User Data object
     * @return Boolean, which indicates if adding of transaction to the blockchain was successful or not
     */
    public Boolean createTransaction(UserData userData){
        if (this.blockChain.isEmpty()) {
            this.blockChain.add(new Block(userData, "0").mineBlock(difficulty));
            System.out.println("Verified and added transaction, size: " + this.blockChain.size());
            return Boolean.TRUE;
        }
        else if(this.isChainValid()){
            this.blockChain.add(new Block(userData, getPreviousBlockHash()).mineBlock(difficulty));
            System.out.println("Verified and added transaction, size: " + (this.blockChain.size()));
            return Boolean.TRUE;
        }
        System.err.println("BlockChain is NOT valid !!");
        return Boolean.FALSE;
    }

    /**
     * Helper function to evaluate of the blockchain is valid so far
     * THis function is called everytime an attempt to add a transaction is made
     *
     * @return Boolean, if the chain is valid or not
     */
    private Boolean isChainValid(){

        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < this.blockChain.size(); i++) {
            Block currentBlock = this.blockChain.get(i);
            Block previousBlock = this.blockChain.get(i-1);

            // check if current hash are valid
            if(!currentBlock.getHash().equals(currentBlock.generateHash())) {
                System.out.println("Current block actual hash: " + currentBlock.generateHash());
                System.out.println("Current block saved hash: " + currentBlock.getHash());

                return Boolean.FALSE;
            }

            // check if previous hash are valid
            if(!previousBlock.generateHash().equals(currentBlock.getPreviousHash())){
                System.out.println("Previous block actual hash: " + previousBlock.generateHash());
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
     * Helper function to get previous block hash
     *
     * @return String
     */
    private String getPreviousBlockHash() {
        return this.blockChain.get(blockChain.size() - 1).getHash();
    }


    /**
     * Overriding toString to print the blockchain in nice json format
     *
     * @return String
     */
    @Override
    public String toString(){
        if(this.blockChain.isEmpty()){
            return "Blockchain is empty!";
        }

        JSONArray jsonArray = new JSONArray();
        for (Block currentBlock : this.blockChain) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username", currentBlock.getUserData().getUsername())
                    .put("Domain", currentBlock.getUserData().getDomain())
                    .put("Password", currentBlock.getUserData().getPassword())
                    .put("Hash", currentBlock.getHash())
                    .put("Previous Hash", currentBlock.getPreviousHash())
                    .put("Timestamp", currentBlock.getTimestamp());
            jsonArray.put(jsonObject);
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonArray);
    }
}
