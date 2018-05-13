import org.junit.jupiter.api.Test;
import sun.security.util.Password;

import static org.junit.jupiter.api.Assertions.*;

class BlockChainTest {

    @Test
    void testCreatingBlockchain() {

        BlockChain blockChain = BlockChain.getBlockChain(); // creating block chain

        assertTrue(blockChain.createTransaction(new UserData("Netflix", "chadha0729", "test123")));
        assertTrue(blockChain.createTransaction(new UserData("Amazon", "gitzy123", "pass123")));

        System.out.println(blockChain.toString());
    }

    @Test
    void isChainValidEmpty() {
        BlockChain blockChain = BlockChain.getBlockChain();
        assertNotNull(blockChain);
    }
}