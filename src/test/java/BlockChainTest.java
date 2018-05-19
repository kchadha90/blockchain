/*
 * Copyright (c) 2018, kchadha
 */

import blockchain.Ledger;
import org.junit.jupiter.api.Test;
import user.UserData;

import static org.junit.jupiter.api.Assertions.*;

class BlockChainTest {

    @Test
    void testCreatingBlockchain() {

        Ledger ledger = Ledger.getInstance(); // creating block chain

        assertTrue(ledger.createTransaction(new UserData("Netflix", "chadha0729", "test123")));
        assertTrue(ledger.createTransaction(new UserData("Amazon", "gitzy123", "pass123")));

        System.out.println(ledger.toString());
    }

    @Test
    void isChainValidEmpty() {
        Ledger blockChain = Ledger.getInstance();
        assertNotNull(blockChain);
    }
}