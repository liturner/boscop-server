package de.turnertech.ows.gml;

import java.io.File;

/**
 * TODO: In the first instance, should offer to take in an XSD, or some kind of "FeatureTypeBuilder". 
 * In any case the FeatureType must not be able to instantiate itself. We have to have a 
 * chance to validate it.
 * 
 * There should be multiple methods. e.g. fromXsd, fromFeatureTypeBuilder etc.
 */
public class FeatureTypeFactory {
    
    public FeatureType decode(File schema) {
        return null;
    }

}
