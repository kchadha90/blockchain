/*
 * Copyright (c) 2018, kchadha
 */

package blockchain;

import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import user.UserData;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Block {
    private UserData userData;
    private String hash;
    private String previousHash;
    private String timestamp;
    private Integer nonce = 0;

    public Block(UserData userData, String previousHash) {
        Objects.requireNonNull(userData, "Data cannot be null");
        if (StringUtil.isNullOrEmpty(previousHash)) {
            throw new IllegalArgumentException();
        }

        this.userData = userData;
        this.previousHash = previousHash;
        this.timestamp = ZonedDateTime.now().toString();
        this.hash = generateHash();
    }

    public Block(UserData userData, String hash, String previous_hash, String timestamp, String nonce) {

        Objects.requireNonNull(userData, "userData cannot be null");
        if (StringUtil.isNullOrEmpty(hash) || StringUtil.isNullOrEmpty(hash)
                || StringUtil.isNullOrEmpty(previous_hash) || StringUtil.isNullOrEmpty(timestamp)) {
            throw new IllegalArgumentException();
        }

        this.userData = userData;
        this.hash = hash;
        this.previousHash = previous_hash;
        this.timestamp = timestamp;
        this.nonce = Integer.parseInt(nonce);
    }

    public String generateHash() {
        return DigestUtils.sha256Hex(this.userData.getUsername()
                + this.userData.getDomain()
                + this.userData.getPassword()
                + getTimestamp()
                + getPreviousHash()
                + getNonce());
    }

    public Block mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"

        while (!this.hash.substring(0, difficulty).equals(target)) {
            this.nonce++;
            this.hash = generateHash();
        }
        System.out.println("Block: Mined, Hash: " + this.hash);
        return this;
    }

    public String getHash() {
        return this.hash;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public String getNonce() {
        return String.valueOf(this.nonce);
    }
}
