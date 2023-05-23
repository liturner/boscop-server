package de.turnertech.thw.cop.gml;

import de.turnertech.thw.cop.ows.api.BoundingBoxProvider;

/**
 * gml:Feature
 * 
 * A DataObject is an abstract "thing" which can be served over the WFS. It
 * has properties and values.
 */
public interface Feature extends BoundingBoxProvider, GmlElement {
    
    public String getId();

    public FeatureType getFeatureType();

}
