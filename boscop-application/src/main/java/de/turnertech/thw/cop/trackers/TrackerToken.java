package de.turnertech.thw.cop.trackers;

import de.turnertech.thw.cop.Logging;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class TrackerToken {

    private TrackerToken() {
        // Static class
    }

    public static String generateKey(final String opta) {
        try {
            final SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
            final String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            return encodedKey;
        } catch (NoSuchAlgorithmException e) {
            Logging.LOG.severe("Could not create a secure API Key for the OPTA");
            return null;
        }
    }

}
