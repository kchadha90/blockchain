import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton BlockChain
 */
public class BlockChain {
    private List<Block> blockChain;
    private static int difficulty = 5;


    private BlockChain() {
        this.blockChain = new ArrayList<>();
    }

    public static BlockChain getBlockChain(){
        return new BlockChain();
    }

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

    private Boolean isChainValid(){

        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < this.blockChain.size(); i++) {
            Block currentBlock = this.blockChain.get(i);
            Block previousBlock = this.blockChain.get(i-1);

            if(!currentBlock.getHash().equals(currentBlock.generateHash())) {
                System.out.println("Current block actual hash: " + currentBlock.generateHash());
                System.out.println("Current block saved hash: " + currentBlock.getHash());

                return Boolean.FALSE;
            }

            if(!previousBlock.generateHash().equals(currentBlock.getPreviousHash())){
                System.out.println("Previous block actual hash: " + previousBlock.generateHash());
                System.out.println("Previous block saved hash: " + currentBlock.getPreviousHash());
                return Boolean.FALSE;
            }

            //check if hash is solved
            if(!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private String getPreviousBlockHash() {
        return this.blockChain.get(blockChain.size() - 1).getHash();
    }

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
