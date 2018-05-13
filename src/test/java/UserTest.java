import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void generateKeyPair() {
        User user = new User("billu", "sample@example.com");
        assertNotSame(user.getPrivateKey(), user.getPublicKey());
        System.out.println(user.getPublicKey());
        System.out.println(user.getPrivateKey());
    }

    @Test
    void getPrivateKey() {
    }

    @Test
    void getPublicKey() {
    }
}