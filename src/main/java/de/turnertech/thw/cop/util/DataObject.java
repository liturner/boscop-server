package de.turnertech.thw.cop.util;

/**
 * A DataObject is an abstract "thing" which can be served over the WFS. It
 * has properties and values.
 */
public interface DataObject extends BoundingBoxProvider, PositionProvider {
    
    public String getId();

    public String toGmlString();

}
