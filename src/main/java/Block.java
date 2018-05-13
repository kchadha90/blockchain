import org.apache.commons.codec.digest.DigestUtils;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

public class Block {
    private UserData userData;
    private String hash;
    private String previousHash;
    private ZonedDateTime timestamp;
    private int nonce = 0;

    public Block(UserData userData, String previousHash) {
        Objects.requireNonNull(userData, "Data cannot be null");
        Objects.requireNonNull(previousHash, "PreviousHash cannot be null");

        this.userData = userData;
        this.previousHash = previousHash;
        this.timestamp = ZonedDateTime.now();
        this.hash = generateHash();
    }

    public String generateHash() {
        return DigestUtils.sha256Hex(this.userData.toString() + this.timestamp.toString() + this.previousHash + this.nonce);
    }

    public Block mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"

        while(!this.hash.substring(0, difficulty).equals(target)) {
            this.nonce ++;
            this.hash = generateHash();
        }
        System.out.println("Block Mined, Hash: " + this.hash);
        return this;
    }

    public String getHash() {
        return this.hash;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public String getTimestamp() {
        return this.timestamp.toString();
    }

    public String getPreviousHash() {
        return this.previousHash;
    }
}
