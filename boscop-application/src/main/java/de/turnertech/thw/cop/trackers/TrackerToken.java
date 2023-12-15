package de.turnertech.thw.cop.trackers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.OPTA;
import de.turnertech.thw.cop.Settings;

/**
 * This class abstracts the storage and retrieval of Tracker and Token pairs from 
 * the rest of the code.
 */
public class TrackerToken {
    
    private static final String FILE_NAME = "TrackerTokens.xml";

    private static Properties trackerTokenPairs = new Properties();

    private TrackerToken() {
        // Static class
    }

    public static boolean exists(String opta) {
        return trackerTokenPairs.containsKey(opta);
    }

    /**
     * @see Properties#entrySet()
     * @return an entry set containing both the keys and values.
     */
    public static Set<Entry<Object, Object>> entrySet() {
        return trackerTokenPairs.entrySet();
    }

    public static boolean isValid(String opta, String key) {
        if(key == null || !OPTA.isValid(opta)) {
            return false;
        }
        return key.equals(trackerTokenPairs.getProperty(opta));
    }

    public static Optional<String> getKey(String opta) {
        return Optional.of(trackerTokenPairs.getProperty(opta));
    }

    /**
     * Will update the existing key if called on an existing OPTA!
     * @param opta
     * @return
     */
    public static Optional<String> generateKey(final String opta) {
        try {
            final SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
            final String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            trackerTokenPairs.setProperty(opta, encodedKey);
            return Optional.of(encodedKey);
        } catch (NoSuchAlgorithmException e) {
            Logging.LOG.severe("Could not create a secure API Key for the OPTA");
            return Optional.empty();
        }
    }

    /**
     * Saves the cached tracker tokens from the internal storage to the directory
     * configured in {@link Settings} under the key {@link Settings#DATA}. Note, 
     * that the file name is fixed.
     */
    public static void save() {
        File saveFile = new File(Settings.getDataDirectory(), FILE_NAME);
        try (FileOutputStream fout = new FileOutputStream(saveFile)) {
            trackerTokenPairs.storeToXML(fout, "Tracker Tocken Pairs", StandardCharsets.UTF_8);
        } catch (IOException e) {
            Logging.LOG.severe("TrackerToken: Could not persist Token Pairs");
        }
    }

    /**
     * Loads the stored tracker tokens from the directory configured in {@link Settings}
     * under the key {@link Settings#DATA}. Note, that the file name is fixed.
     */
    public static void load() {
        File saveFile = new File(Settings.getDataDirectory(), FILE_NAME);
        try (FileInputStream fin = new FileInputStream(saveFile)) {
            trackerTokenPairs.clear();
            trackerTokenPairs.loadFromXML(fin);
            Logging.LOG.fine("TrackerToken: Loaded Token Pairs");
        } catch (IOException e) {
            Logging.LOG.severe("TrackerToken: Could not load Token Pairs");
        }
    }
}
