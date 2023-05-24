package de.turnertech.thw.cop.util;

import java.util.List;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.IFeature;

public interface BoundingBoxFilter {
    
    public List<IFeature> filter(BoundingBox boundingBox);

}
