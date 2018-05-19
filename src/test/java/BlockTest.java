/*
 * Copyright (c) 2018, kchadha
 */

import blockchain.Block;
import blockchain.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import user.UserData;

import java.util.ArrayList;
import java.util.List;

import static blockchain.Ledger.difficulty;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testBlock() {
        Block b1 = new Block(new UserData("abc@", "test123", "pass123"), "0");
        assertNotNull(b1);
        assertEquals("0", b1.getPreviousHash());
        System.out.println("Hash for block one: " + b1.getHash());

        Block b2 = new Block(new UserData("abc!", "test123", "pass123"), b1.getHash());
        assertNotNull(b2);
        assertNotEquals(b2.getHash(), b1.getHash());
        assertEquals(b2.getPreviousHash(), b1.getHash());
        System.out.println("Hash for block two: " + b2.getHash());

        Block b3 = new Block(new UserData("abc#", "test123", "pass123"), b2.getHash());
        assertNotNull(b3);
        assertNotEquals(b3.getHash(), b2.getHash());
        assertEquals(b3.getPreviousHash(), b2.getHash());
        System.out.println("Hash for block three: " + b3.getHash());
    }

    @Test
    void hashTest() {
        Block b1 = new Block(new UserData("abc@", "test123", "pass123"), "123456")
                .mineBlock(difficulty);
        Block b2 = new Block(new UserData("abasdc@", "asd", "pass12asd3"), b1.getHash())
                .mineBlock(difficulty);
        List<Block> ledger = new ArrayList();
        ledger.add(b1);
        ledger.add(b2);
        assertEquals(b1.generateHash(), b1.getHash());
        assertEquals(DigestUtils.sha256Hex(b1.getUserData().getUsername()
                + b1.getUserData().getDomain()
                + b1.getUserData().getPassword()
                + b1.getTimestamp()
                + b1.getPreviousHash()
                + b1.getNonce()),  b1.getHash());
        assertTrue(Utils.isChainValid(ledger));

    }

    @Test
    void test3(){
        assertEquals(DigestUtils.sha256Hex("examplechadhatest1232018-05-19T11:00:39.552-04:00[America/New_York]04f96c687b1129802b0319638f0dc9990ed2541a802429d49a7f6b5459be0dc60")
                , DigestUtils.sha256Hex("chadhaexampletest1232018-05-19T11:00:39.552-04:00[America/New_York]04f96c687b1129802b0319638f0dc9990ed2541a802429d49a7f6b5459be0dc60"));
}
    }