import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testBlock(){
        Block b1 = new Block(new UserData("abc@","test123", "pass123"), "0");
        assertNotNull(b1);
        assertEquals("0", b1.getPreviousHash());
        System.out.println("Hash for block one: " + b1.getHash());

        Block b2 = new Block(new UserData("abc!","test123", "pass123"), b1.getHash());
        assertNotNull(b2);
        assertNotEquals(b2.getHash(), b1.getHash());
        assertEquals(b2.getPreviousHash(), b1.getHash());
        System.out.println("Hash for block two: " + b2.getHash());

        Block b3 = new Block(new UserData("abc#","test123", "pass123"), b2.getHash());
        assertNotNull(b3);
        assertNotEquals(b3.getHash(), b2.getHash());
        assertEquals(b3.getPreviousHash(), b2.getHash());
        System.out.println("Hash for block three: " + b3.getHash());
    }
}