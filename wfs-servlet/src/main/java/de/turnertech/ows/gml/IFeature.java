package de.turnertech.ows.gml;

/**
 * gml:Feature
 * 
 * A DataObject is an abstract "thing" which can be served over the WFS. It
 * has properties and values.
 */
public interface IFeature extends BoundingBoxProvider, GmlElement {
    
    public String getId();

    public FeatureType getFeatureType();

    public Object getPropertyValue(String propertyName);

    public boolean hasPropertyValue(String propertyName);

    public Object setPropertyValue(String propertyName, Object value);

}
