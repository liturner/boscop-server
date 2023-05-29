package de.turnertech.thw.cop.persistance;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.OPTA;

/**
 * This class abstracts the storage and retrieval of Tracker and Token pairs from 
 * the rest of the code.
 */
public class TrackerToken {
    
    private static HashMap<String, String> trackerTokenPairs = new HashMap<>();

    private TrackerToken() {
    }

    public static boolean exists(String opta) {
        return trackerTokenPairs.containsKey(opta);
    }

    public static boolean isValid(String opta, String key) {
        if(key == null || !OPTA.isValid(opta)) {
            return false;
        }
        return key.equals(trackerTokenPairs.get(opta));
    }

    public static Optional<String> getKey(String opta) {
        return Optional.of(trackerTokenPairs.get(opta));
    }

    /**
     * Will update the existing key if called on an existing OPTA!
     * @param opta
     * @return
     */
    public static Optional<String> generateKey(final String opta) {
        try {
            final SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
            //final String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            final String encodedKey = "qwe";
            trackerTokenPairs.put(opta, encodedKey);
            return Optional.of(encodedKey);
        } catch (NoSuchAlgorithmException e) {
            Logging.LOG.severe("Could not create a secure API Key for the OPTA");
            return Optional.empty();
        }
    }
}
