package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

/**
 * This type is a function, as any value references will need the input Feature
 * to be able to aquire the actual value. Any literals would simply ignore
 * the passed in Feature.
 */
public interface Expression extends java.util.function.Function<IFeature, Object> {
    
}
