package de.turnertech.thw.cop;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {

    private static final Properties settingsStorage;

    public static final String PORT = "boscop.port";
    public static final String PATH = "boscop.path";
    public static final String DATA = "boscop.data";
    public static final String CFG = "boscop.cfg";

    static {
        settingsStorage = new Properties();
        settingsStorage.setProperty(PORT, "8080");
        settingsStorage.setProperty(PATH, "/");
        settingsStorage.setProperty(DATA, "data");
        settingsStorage.setProperty(CFG, "cfg");
    }

    private Settings() {
        
    }

    public static void parseArguments(String[] args) {
        settingsStorage.getOrDefault(args, args);
    }

    public static File getDataDirectory() {
        return Path.of(settingsStorage.getProperty(DATA)).toAbsolutePath().toFile();
    }

    public static File getConfigDirectory() {
        return Path.of(settingsStorage.getProperty(CFG)).toAbsolutePath().toFile();
    }

    public static File getFeatureTypeDirectory() {
        return Path.of(settingsStorage.getProperty(CFG), "feature-types").toAbsolutePath().toFile();
    }

    public static int getPort() {
        return Integer.valueOf(settingsStorage.getProperty(PORT));
    }
    
}
