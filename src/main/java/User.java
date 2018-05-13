import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class User {

    private final String emailAddress;
    private final String name;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public User(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrivateKey() {
        String publicKeyBytes = Base64.encode(this.privateKey.getEncoded());
        return publicKeyBytes;
    }

    public String getPublicKey() {
        String publicKeyBytes = Base64.encode(this.publicKey.getEncoded());
        return publicKeyBytes;
    }
}
