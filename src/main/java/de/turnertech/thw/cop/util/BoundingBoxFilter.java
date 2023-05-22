package de.turnertech.thw.cop.util;

import java.util.List;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;

public interface BoundingBoxFilter {
    
    public List<Feature> filter(BoundingBox boundingBox);

}
